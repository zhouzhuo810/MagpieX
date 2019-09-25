package me.zhouzhuo810.magpiex.ui.fgm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.disposables.Disposable;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.act.IBaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

public abstract class BaseFragment extends Fragment implements IBaseFragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected View rootView;
    protected boolean isVisible;
    protected long mCallLazyLoadCount;
    protected boolean mNeedLazeLoaded = true;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            if (getFragmentManager() != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commit();
            }
        }
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
    
    public static <T extends BaseFragment> T newInstance(Class<T> clazz, Bundle args) {
        String fname = clazz.getSimpleName();
        try {
            T t = clazz.getConstructor().newInstance();
            if (args != null) {
                args.setClassLoader(t.getClass().getClassLoader());
                t.setArguments(args);
            }
            return t;
        } catch (InstantiationException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                + ": make sure class name exists, is public, and has an"
                + " empty constructor that is public", e);
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                + ": make sure class name exists, is public, and has an"
                + " empty constructor that is public", e);
        } catch (NoSuchMethodException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                + ": could not find Fragment constructor", e);
        } catch (InvocationTargetException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                + ": calling Fragment constructor caused an exception", e);
        } catch (java.lang.InstantiationException e) {
            throw new InstantiationException("Unable to instantiate fragment " + fname
                + ": calling Fragment constructor caused an exception", e);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        //屏幕适配
        ScreenAdapterUtil.getInstance().loadView(rootView);
        
        if (!shouldNotInvokeInitMethods(savedInstanceState)) {
            initView(savedInstanceState);
            initData();
            initEvent();
        }
        
        return rootView;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (isVisible && isVisible() && mCallLazyLoadCount == 0) {
            // 界面第一次显示且未调用过数据懒加载方法
            mCallLazyLoadCount++;
            onVisible();
        }
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 只能表明界面可能初始化并显示
            isVisible = true;
            if (isVisible()) {
                // 界面显示
                mCallLazyLoadCount++;
                onVisible();
            }
        } else if (isVisible) {
            // 界面退出
            isVisible = false;
            onInvisible();
        }
    }
    
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
    
    protected void onVisible() {
        if (needLazyLoadData()) {
            lazyLoadData();
        }
    }
    
    
    protected void onInvisible() {
    
    }
    
    @Override
    public void lazyLoadData() {
    
    }
    
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().overridePendingTransition(enterAnim, exitAnim);
    }
    
    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        if (rootView == null) {
            return null;
        }
        return rootView.findViewById(id);
    }
    
    @Override
    public IBaseActivity getBaseAct() {
        return (IBaseActivity) getActivity();
    }
    
    @Override
    public void closeAct() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().closeAct();
    }
    
    @Override
    public void closeAct(boolean defaultAnimation) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().closeAct(defaultAnimation);
    }
    
    @Override
    public void closeAllAct() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().closeAllAct();
    }
    
    /**
     * 跳转到目标Activity
     *
     * @param clazz 目标界面
     */
    @Override
    public void startAct(Class<? extends Activity> clazz) {
        startActWithIntent(new Intent(getContext(), clazz));
    }
    
    @Override
    public void startActForResult(Class<? extends Activity> clazz, int requestCode) {
        startActWithIntentForResult(new Intent(getContext(), clazz), requestCode);
    }
    
    @Override
    public void startActWithIntent(Intent intent) {
        startActWithIntent(intent, false);
    }
    
    @Override
    public void startActWithIntent(Intent intent, boolean defaultAnim) {
        if (defaultAnim) {
            startActivity(intent);
        } else {
            startActivity(intent);
            overridePendingTransition(openInAnimation(), openOutAnimation());
            
        }
    }
    
    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode) {
        startActWithIntentForResult(intent, requestCode, false);
    }
    
    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim) {
        if (defaultAnim) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivityForResult(intent, requestCode);
            overridePendingTransition(openInAnimation(), openOutAnimation());
        }
    }
    
    @Override
    public void showLoadingDialog(String msg) {
        showLoadingDialog(null, msg);
    }
    
    @Override
    public void showLoadingDialog(String title, String msg) {
        showLoadingDialog(title, msg, false, null);
    }
    
    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable) {
        showLoadingDialog(title, msg, cancelable, false, null);
    }
    
    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle) {
        showLoadingDialog(title, msg, cancelable, iosStyle, null);
    }
    
    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener listener) {
        showLoadingDialog(title, msg, cancelable, false, null);
    }
    
    @Override
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showLoadingDialog(title, msg, cancelable, iosStyle, onDismissListener);
    }
    
    @Override
    public void hideLoadingDialog() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideLoadingDialog();
    }
    
    @Override
    public void showOneBtnProgressDialog(String title, String msg, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, null, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(String title, String msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, onDismissListener, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, getString(R.string.magpie_ok_text), cancelable, onDismissListener, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(String title, String msg, String btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showOneBtnProgressDialog(title, msg, btnString, cancelable, onDismissListener, onProgressListener);
    }
    
    @Override
    public void hideOneBtnProgressDialog() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideOneBtnProgressDialog();
    }
    
    @Override
    public void showTwoBtnTextDialog(String title, String msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, false, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, cancelable, null, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(String title, String msg, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showTwoBtnTextDialog(title, msg, leftBtnString, rightBtnString, cancelable, onDismissListener, onTwoBtnClick);
    }
    
    @Override
    public void hideTwoBtnTextDialog() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideTwoBtnTextDialog();
    }
    
    @Override
    public void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, cancelable, null, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, inputType, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(String title, String msg, String hint, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, InputType.TYPE_CLASS_TEXT, getString(R.string.magpie_cancel_text), getString(R.string.magpie_ok_text), cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showTwoBtnEditDialog(title, msg, hint, inputType, leftBtnString, rightBtnString, cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void hideTwoBtnEditDialog() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideTwoBtnEditDialog();
    }
    
    @Override
    public void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(null, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, false, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showListDialog(title, items, alignLeft, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public void hideListDialog() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideListDialog();
    }
    
    @Override
    public void refreshData(String... params) {
    
    }
    
    @Override
    public void loadMoreData(String... params) {
    
    }
    
    @Override
    public void showBottomSheet(String title, List<String> items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, items, false, cancelable, onItemClick);
    }
    
    @Override
    public void showBottomSheet(String title, String[] items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), false, cancelable, onItemClick);
    }
    
    @Override
    public void showBottomSheet(String title, List<String> items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, items, alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showBottomSheet(String title, String[] items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showBottomSheet(String title, String[] items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), alignLeft, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public void showBottomSheet(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick) {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().showBottomSheet(title, items, alignLeft, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public TextWatcher setEditImageListener(EditText et, ImageView iv) {
        if (getBaseAct() == null) {
            return null;
        }
        return getBaseAct().setEditImageListener(et, iv);
    }
    
    @Override
    public void hideBottomSheet() {
        if (getBaseAct() == null) {
            return;
        }
        getBaseAct().hideBottomSheet();
    }
    
    @Override
    public int closeInAnimation() {
        if (getBaseAct() == null) {
            return 0;
        }
        return getBaseAct().closeInAnimation();
    }
    
    @Override
    public int closeOutAnimation() {
        if (getBaseAct() == null) {
            return 0;
        }
        return getBaseAct().closeOutAnimation();
    }
    
    @Override
    public int openInAnimation() {
        if (getBaseAct() == null) {
            return 0;
        }
        return getBaseAct().openInAnimation();
    }
    
    @Override
    public int openOutAnimation() {
        if (getBaseAct() == null) {
            return 0;
        }
        return getBaseAct().openOutAnimation();
    }
    
    @Override
    public Fragment findFragmentByTag(String tag) {
        return getChildFragmentManager().findFragmentByTag(tag);
    }
    
    /**
     * 判断是否需要懒加载数据，此方法只会允许调用一次懒加载，如果需要界面每次重绘时都加载数据，覆写该方法，一直返回true即可
     *
     * @return {@code true} 需要懒加载，则方法{@link #lazyLoadData()}将被调用
     * {@code false} 不需要懒加载
     */
    private boolean needLazyLoadData() {
        final boolean needLoad = mNeedLazeLoaded;
        if (mNeedLazeLoaded) {
            mNeedLazeLoaded = false;
        }
        return needLoad;
    }
    
    public boolean isNeedLazyLoad() {
        return mNeedLazeLoaded;
    }
    
    public long getCallLazyLoadCount() {
        return mCallLazyLoadCount;
    }
    
    public void clearCallLazyLoadCount() {
        mCallLazyLoadCount = 0;
    }
    
    @Override
    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
