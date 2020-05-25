package me.zhouzhuo810.magpiex.ui.fgm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
    
    /**
     * 当前界面是否结合ViewPager使用
     */
    private boolean mCombineViewPager;
    
    /**
     * 通常是FragmentPagerAdapter中调用{@link Fragment#setUserVisibleHint(boolean)}.
     * 如果主动调用{@link Fragment#setUserVisibleHint(boolean)}，也会判定当前界面是结合ViewPager
     * 使用，因此请自行处理好相应的界面展示隐藏逻辑
     */
    private boolean mVisibleToUser;
    
    /**
     * 是否在onResume生命周期调用Visible方法
     */
    private boolean mOnResumeCallVisible;
    
    /**
     * 是否在onResume生命周期调用Child Fragment的{@link Fragment#setUserVisibleHint(boolean)}
     */
    private boolean mOnResumeCallChildSetUserVisibleHint;
    
    private boolean mNeedLazeLoaded = true;
    
    private boolean mDestroy;
    
    /**
     * 界面是否真的对于用户可见
     */
    private boolean mViewActualVisible;
    
    
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
        if (mCombineViewPager) {
            if (mVisibleToUser && mOnResumeCallVisible && isVisible()) {
                viewVisibleToUser(true);
            }
        
            if (mOnResumeCallChildSetUserVisibleHint) {
                // 如果父Fragment是结合ViewPager使用，则
                FragmentManager childFragmentManager = getChildFragmentManager();
                List<Fragment> fragments = childFragmentManager.getFragments();
                for (Fragment fragment : fragments) {
                    fragment.setUserVisibleHint(mViewActualVisible);
                }
            }
        } else if (isVisible()) {
            viewVisibleToUser(true);
        }
        
    }
    
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
    
    /**
     * 延迟加载数据
     */
    protected void lazyLoadData() {
    
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
    
    
    public boolean isNeedLazyLoad() {
        return mNeedLazeLoaded;
    }
    
    @Override
    public void onStop() {
        super.onStop();
        viewVisibleToUser(false);
    }
    
    /**
     * 设置界面是否可见于用户，通常是FragmentPagerAdapter中调用{@link Fragment#setUserVisibleHint(boolean)}.
     * 如果主动调用{@link Fragment#setUserVisibleHint(boolean)}，也会判定当前界面是结合ViewPager
     * 使用，因此请自行处理好相应的界面展示隐藏逻辑。不建议手动调用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mCombineViewPager = true;
        if (isVisibleToUser) {
            // 只能表明界面可能初始化并显示
            mVisibleToUser = true;
            if (isVisible()) {
                // 界面显示
                mOnResumeCallVisible = false;
                viewVisibleToUser(true);
            } else {
                // 此时界面还未创建好，生命周期并未执行，结合ViewPager使用时，此方法会先于生命周期执行
                mOnResumeCallVisible = true;
            }
        } else {
            mVisibleToUser = false;
            viewVisibleToUser(false);
        }
        
        if (getHost() == null) {
            mOnResumeCallChildSetUserVisibleHint = true;
            return;
        }
        
        mOnResumeCallChildSetUserVisibleHint = false;
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(isVisibleToUser);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDestroy = true;
    }
    
    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        viewVisibleToUser(!hidden);
        // 当onHiddenChanged被调用时，往往只有被隐藏的Fragment执行这个方法，对于Fragment的ChildFragmentManager管理的Fragment
        // 往往没有任何变化，这时如果我们想在子Fragment根据界面展示隐藏做一些逻辑就无法实现，而且当外层Fragment hidden时，子Fragment
        // 调用isVisible()返回的还是true。因此执行以下逻辑，当上层Fragment hidden时，其管理的子Fragment同样执行相同的逻辑
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            boolean hidden2 = fragment.isHidden();
            if (hidden == hidden2) {
                continue;
            }
            
            if (hidden) {
                fragmentTransaction.hide(fragment);
            } else {
                fragmentTransaction.show(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
    
    /**
     * 界面是否真实可见于用户
     */
    private void viewVisibleToUser(boolean visible) {
        if (getHost() == null) {
            return;
        }
        
        if (mViewActualVisible == visible) {
            return;
        }
        
        mViewActualVisible = visible;
        if (visible) {
            onVisible();
            if (mNeedLazeLoaded) {
                mNeedLazeLoaded = false;
                lazyLoadData();
            }
        } else {
            onInvisible();
        }
    }
    
    public boolean isDestroy() {
        return mDestroy;
    }
    
    /**
     * 界面可见
     */
    protected void onVisible() {
    
    }
    
    /**
     * 界面不可见
     */
    protected void onInvisible() {
    
    }
    
    /**
     * 刷新数据，可在任何时机调用。如果调用时，界面并没有显示，或者压根就没有走生命周期。此时会标记为延迟加载，
     * 在界面真正显示到界面时，会调用{@link #lazyLoadData()}。如果传参，子类覆盖该类处理参数保存相关操作，
     * 但是记得参数更新后再调用super.refreshDataIfNeeded(params)
     */
    @CallSuper
    public void refreshDataIfNeeded(Object... params) {
        if (mViewActualVisible) {
            lazyLoadData();
        } else {
            mNeedLazeLoaded = true;
        }
    }
    
    public boolean isViewActualVisible() {
        return mViewActualVisible;
    }
    
    /**
     * 按下返回键
     *
     * @return {@code true} 消费此次返回事件，{@code false} 不消费此次返回事件
     */
    public boolean onBackPressed() {
        return doKeyEvent(0, -1, null);
    }
    
    /**
     * 按键监听
     *
     * @return {@code true}处理此次事件，{@code false}不处理
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doKeyEvent(1, keyCode, event);
    }
    
    /**
     * 按键分发
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        return doKeyEvent(2, -1, event);
    }
    
    private boolean doKeyEvent(int type, int keyCode, KeyEvent event) {
        FragmentManager manager = getChildFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                if (type == 0 && ((BaseFragment) fragment).onBackPressed()) {
                    return true;
                } else if (type == 1 && ((BaseFragment) fragment).onKeyDown(keyCode, event)) {
                    return true;
                } else if (type == 2 && ((BaseFragment) fragment).dispatchKeyEvent(event)) {
                    return true;
                }
            }
        }
        
        int backStackEntryCount = manager.getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            return false;
        }
        
        FragmentManager.BackStackEntry backStackEntryAt
            = manager.getBackStackEntryAt(backStackEntryCount - 1);
        Fragment fragment = manager.findFragmentByTag(backStackEntryAt.getName());
        if (fragment instanceof BaseFragment) {
            if (type == 0 && ((BaseFragment) fragment).onBackPressed()) {
                return true;
            } else if (type == 1 && ((BaseFragment) fragment).onKeyDown(keyCode, event)) {
                return true;
            } else {
                return type == 2 && ((BaseFragment) fragment).dispatchKeyEvent(event);
            }
        } else {
            return false;
        }
    }
    
    @Override
    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
