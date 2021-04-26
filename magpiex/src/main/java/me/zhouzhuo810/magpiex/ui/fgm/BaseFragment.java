package me.zhouzhuo810.magpiex.ui.fgm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.rxjava3.disposables.Disposable;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.ui.act.IBaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;

/**
 * 此BaseFragment主要提供了界面展示隐藏，数据延迟加载方法，使用者无需关心界面显示和隐藏的时机，
 * 只需要关心在界面显示时{@link #onVisible()},界面隐藏时{@link #onInvisible()}
 * 以及初次加载需要延迟刷新的数据{@link #lazyLoadData()},该方法调用在{@link #onVisible()}之后。
 * 而且提供了一个刷新数据的方法{@link #refreshDataIfNeeded(Object...)}，使用者无需关心需要刷新的界面是否显示，
 * 只需要关心界面是否需要刷新，调用{@link #refreshDataIfNeeded(Object...)}后，如果当前界面对于用户可见，则立即
 * 调用{@link #lazyLoadData()}，否则在界面下次展示后调用{@link #lazyLoadData()}
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment, View.OnTouchListener {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    // 是否是结合ViewPager使用，在被回收时需要保存起来，否则下次恢复时，优先执行的是OnResume
    // 而不是setUserVisibleHint,正常结合ViewPager是先调用setUserVisibleHint
    private static final String COMBINE_VIEW_PAGER = "CombineViewPager";
    // 记录当前Fragment hide时，管理的childFragment显示状态，在被回收后恢复时使用
    private static final String PRE_SHOW_CHILD_FRAGMENT_BY_HIDDEN = "preShowChildFragment";
    // 记录当前Fragment setUserVisibleHint(false)时，管理的childFragment显示状态，在被回收后恢复时使用
    private static final String PRE_SHOW_CHILD_FRAGMENT_BY_USER_VISIBLE_HINT = "preShowChildFragmentByUserVisibleHint";
    
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
    
    /**
     * 是否需要延迟加载
     */
    private boolean mNeedLazeLoaded = true;
    
    /**
     * 当前界面是否被销毁
     */
    private boolean mDestroy;
    
    /**
     * 界面是否真的对于用户可见
     */
    private boolean mViewActualVisible;
    
    /**
     * 记录当前Fragment {@link #onHiddenChanged(boolean)}为false之前，
     * 此时ChildFragment显示的界面，用于下次恢复显示时使用
     */
    private ArrayList<String> mPreShowChildFragmentByHidden = new ArrayList<>();
    
    /**
     * 记录当前Fragment {@link #setUserVisibleHint(boolean)}为false之前，
     * 此时ChildFragment显示的界面，用于下次恢复显示时使用
     */
    private ArrayList<String> mPreShowChildFragmentByUserVisibleHint = new ArrayList<>();
    
    /**
     * 系统返回按钮点击回调
     */
    OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            boolean backPressed = onBackPressed();
            if (backPressed) {
                return;
            }
            setEnabled(false);
            mOnBackPressedDispatcher.onBackPressed();
            setEnabled(true);
        }
    };
    /**
     * host Activity返回点击分发
     */
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestoredInstanceState(savedInstanceState);
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
        outState.putBoolean(COMBINE_VIEW_PAGER, mCombineViewPager);
        outState.putStringArrayList(PRE_SHOW_CHILD_FRAGMENT_BY_HIDDEN, mPreShowChildFragmentByHidden);
        outState.putStringArrayList(PRE_SHOW_CHILD_FRAGMENT_BY_USER_VISIBLE_HINT, mPreShowChildFragmentByUserVisibleHint);
    }
    
    /**
     * 恢复数据，在{@link #onCreate(Bundle)}方法中执行此方法，在系统onCreate之后。
     * 注意，如果需要销毁数据，建议放在此方法中销毁老数据，因为调用{@link #onSaveInstanceState(Bundle)}
     * 并不表示当前对象被回收，只是有可能进行回收，因此在此方法中判断savedInstanceState不为null时进行销毁更加稳妥
     */
    @CallSuper
    public void onRestoredInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        
        mCombineViewPager = savedInstanceState.getBoolean(COMBINE_VIEW_PAGER);
        mPreShowChildFragmentByHidden = savedInstanceState.getStringArrayList(PRE_SHOW_CHILD_FRAGMENT_BY_HIDDEN);
        mPreShowChildFragmentByUserVisibleHint = savedInstanceState.getStringArrayList(PRE_SHOW_CHILD_FRAGMENT_BY_USER_VISIBLE_HINT);
        if (mPreShowChildFragmentByHidden == null) {
            mPreShowChildFragmentByHidden = new ArrayList<>();
        }
        
        if (mPreShowChildFragmentByUserVisibleHint == null) {
            mPreShowChildFragmentByUserVisibleHint = new ArrayList<>();
        }
    }
    
    /**
     * 初始化参数Bundle，如果已经初始化，则直接返回，否则初始化并绑定到当前{@link Fragment}
     */
    protected Bundle initArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            setArguments(arguments);
        }
        return arguments;
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
        rootView.setOnTouchListener(this);
        // 屏幕适配
        SimpleUtil.scaleView(rootView);
        return rootView;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (!isResumed()) {
                    return;
                }
                if (mCombineViewPager) {
                    if (mVisibleToUser && isVisibleInner()) {
                        viewVisibleToUser(true);
                    }
                } else if (isVisibleInner()) {
                    viewVisibleToUser(true);
                }
            }
            
            @Override
            public void onViewDetachedFromWindow(View v) {
                if (!isResumed()) {
                    return;
                }
                v.removeOnAttachStateChangeListener(this);
                viewVisibleToUser(false);
            }
        });
        
        if (!shouldNotInvokeInitMethods(savedInstanceState)) {
            initView(savedInstanceState);
            initData();
            initEvent();
        }
    }
    
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        
        mOnBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        mOnBackPressedDispatcher.addCallback(this, mOnBackPressedCallback);
    }
    
    /**
     * 判断界面是否可见，此方法与{@link #isVisible()}的区别在于不管{@link #getView()}的可见性如何，
     * 只要其它条件为true，则认为界面可见，此举的目的是防止将{@link #getView()}设置为非{@link View#VISIBLE},
     * 在Fragment周期方法走完，只是由于界面可见性为不可见，导致{@link #onVisible()}和{@link #lazyLoadData()}不调用。
     * 此时可能更多的是希望调用{@link #onVisible()}和{@link #lazyLoadData()}，以此来处理{@link #getView()}设置
     * 为可见或不可见以及其它一些业务逻辑
     */
    private boolean isVisibleInner() {
        return isAdded() && !isHidden() && getView() != null && getView().getWindowToken() != null;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (mCombineViewPager) {
            if (mVisibleToUser && mOnResumeCallVisible && isVisibleInner()) {
                viewVisibleToUser(true);
            }
            
            if (mOnResumeCallChildSetUserVisibleHint) {
                setChildFragmentUserVisibleHint(mViewActualVisible);
            }
        } else if (isVisibleInner()) {
            viewVisibleToUser(true);
        }
        
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
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
    
    @Override
    public void onPause() {
        super.onPause();
        if (mViewActualVisible) {
            // 此举目的是记录如果是因为跳转其它界面，导致当前界面不可见
            // 在下次界面可见时，在onResume中，如果是结合ViewPager使用
            // 则直接在onResume调用viewVisibleToUser(true),因为此时
            // 界面恢复可见不会调用setUserVisibleHint
            mOnResumeCallVisible = true;
        }
        viewVisibleToUser(false);
    }
    
    /**
     * 设置界面是否可见于用户，通常是FragmentPagerAdapter中调用{@link Fragment#setUserVisibleHint(boolean)}.
     * 如果主动调用{@link Fragment#setUserVisibleHint(boolean)}，也会判定当前界面是结合ViewPager
     * 使用，因此请自行处理好相应的界面展示隐藏逻辑，不建议手动调用。
     */
    @Deprecated
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mCombineViewPager = true;
        if (isVisibleToUser) {
            // 只能表明界面可能初始化并显示
            mVisibleToUser = true;
            if (isVisibleInner()) {
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
        // ViewPager调用setUserVisibleHint只负责直接Fragment,如果直接Fragment中继续嵌套Fragment，
        // 则不会处理，因此此处需要手动处理
        setChildFragmentUserVisibleHint(isVisibleToUser);
    }
    
    /**
     * 设置当前Fragment管理的Child Fragment可见性
     */
    private void setChildFragmentUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            mPreShowChildFragmentByUserVisibleHint.clear();
        }
        
        List<Fragment> fragments = getChildFragment();
        for (Fragment fragment : fragments) {
            if (!(fragment instanceof BaseFragment)) {
                continue;
            }
            
            BaseFragment baseFragment = (BaseFragment) fragment;
            if (!isVisibleToUser) {
                if (baseFragment.isViewActualVisible()) {
                    mPreShowChildFragmentByUserVisibleHint.add(baseFragment.getTag());
                    baseFragment.setUserVisibleHint(false);
                }
            } else if (!baseFragment.isViewActualVisible()) {
                if (mPreShowChildFragmentByUserVisibleHint.contains(fragment.getTag())) {
                    baseFragment.setUserVisibleHint(true);
                }
            }
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
        if (hidden) {
            viewVisibleToUser(false);
        } else if (mCombineViewPager) {
            if (mVisibleToUser && isVisibleInner()) {
                viewVisibleToUser(true);
            }
        } else if (isVisibleInner()) {
            viewVisibleToUser(true);
        }
        
        // 当onHiddenChanged被调用时，往往只有被隐藏的Fragment执行这个方法，对于Fragment的ChildFragmentManager管理的Fragment
        // 往往没有任何变化，这时如果我们想在子Fragment根据界面展示隐藏做一些逻辑就无法实现，而且当外层Fragment hidden时，子Fragment
        // 调用isVisible()返回的还是true。因此执行以下逻辑，当上层Fragment hidden时，其管理的子Fragment同样执行相同的逻辑
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        List<Fragment> fragments = getChildFragment();
        if (hidden) {
            mPreShowChildFragmentByHidden.clear();
        }
        for (Fragment fragment : fragments) {
            if (!(fragment instanceof BaseFragment)) {
                continue;
            }
            
            BaseFragment baseFragment = (BaseFragment) fragment;
            if (hidden) {
                if (!baseFragment.isHidden()) {
                    // 不能使用 baseFragment.isViewActualVisible() 为true时作为判定条件将fragment添加到mPreShowChildFragmentByHidden
                    // 因为使用该条件，在同一时间只会有一个界面是true，然而此时需要保留下次恢复的界面是本次状态是否是Hidden，而不是是否真实可见
                    mPreShowChildFragmentByHidden.add(fragment.getTag());
                    fragmentTransaction.hide(baseFragment);
                }
            } else if (baseFragment.isHidden()) {
                if (mPreShowChildFragmentByHidden.contains(baseFragment.getTag())) {
                    fragmentTransaction.show(baseFragment);
                }
            }
        }
        
        // 此处一定要使用commitNow，否则上面的fragment.isHidden()会存在延迟性，导致结果与实际不符
        fragmentTransaction.commitNowAllowingStateLoss();
    }
    
    /**
     * 界面是否真实可见于用户
     */
    private void viewVisibleToUser(boolean viewActualVisible) {
        if (getHost() == null) {
            return;
        }
        
        if (mViewActualVisible == viewActualVisible) {
            return;
        }
        
        mViewActualVisible = viewActualVisible;
        if (viewActualVisible) {
            String simpleName = getClass().getSimpleName();
            SpUtil.putString(Cons.SP_KEY_OF_CURRENT_ACTIVITY_OR_FRAGMENT_NAME, simpleName);
            visible();
            if (mNeedLazeLoaded) {
                mNeedLazeLoaded = false;
                lazyLoadData();
            }
        } else {
            invisible();
        }
    }
    
    public boolean isDestroy() {
        return mDestroy;
    }
    
    @CallSuper
    protected void visible() {
        if (mOnBackPressedCallback != null) {
            mOnBackPressedCallback.setEnabled(true);
        }
        onVisible();
    }
    
    
    /**
     * 界面可见，此可见是指物理可见，但是不包括将{@link #getView()}设置为非{@link View#VISIBLE}
     * 的情况，我希望的是此方法调用时，在UI栈中，栈顶对象就是当前对象，因此不管当前对象的根布局是否可见。
     */
    protected void onVisible() {
    
    }
    
    @CallSuper
    protected void invisible() {
        if (mOnBackPressedCallback != null) {
            mOnBackPressedCallback.setEnabled(false);
        }
        onInvisible();
    }
    
    /**
     * 界面不可见
     */
    protected void onInvisible() {
    
    }
    
    /**
     * 刷新数据，可在任何时机调用。如果调用时，界面并没有显示，或者压根就没有走生命周期。此时会标记为延迟加载，
     * 在界面真正显示到界面时，会调用{@link #lazyLoadData()}。如果传参，子类覆盖该类处理参数保存相关操作，
     * 但是记得参数更新后"再"调用super.refreshDataIfNeeded(params)
     */
    @CallSuper
    public void refreshDataIfNeeded(Object... params) {
        refreshDataIfNeeded();
    }
    
    /**
     * 刷新数据，可在任何时机调用。如果调用时，界面并没有显示，或者压根就没有走生命周期。此时会标记为延迟加载，
     * 在界面真正显示到界面时，会调用{@link #lazyLoadData()}。如果传参，子类覆盖该类处理参数保存相关操作，
     * 但是记得参数更新后"再"调用super.refreshDataIfNeeded(params)
     */
    @CallSuper
    public void refreshDataIfNeeded(Bundle params) {
        refreshDataIfNeeded();
    }
    
    /**
     * 刷新数据，可在任何时机调用。如果调用时，界面并没有显示，或者压根就没有走生命周期。此时会标记为延迟加载，
     * 在界面真正显示到界面时，会调用{@link #lazyLoadData()}。
     */
    public final void refreshDataIfNeeded() {
        if (mViewActualVisible) {
            mNeedLazeLoaded = false;
            lazyLoadData();
        } else {
            mNeedLazeLoaded = true;
        }
    }
    
    /**
     * 延迟加载数据，{@link #onResume()}之后调用
     */
    @CallSuper
    protected void lazyLoadData() {
        onLazyLoadData();
    }
    
    /**
     * 执行自定义延迟加载数据逻辑，{@link #lazyLoadData()}之后调用
     */
    protected void onLazyLoadData() {
    
    }
    
    /**
     * 判断界面在下次展示时是否需要调用{@link #lazyLoadData()}方法
     */
    @Override
    public boolean isNeedLazyLoad() {
        return mNeedLazeLoaded;
    }
    
    /**
     * 界面是否真实可见于用户，此可见指物理可见而不是逻辑可见,但是不包括将{@link #getView()}设置为非{@link View#VISIBLE}的情况
     */
    public boolean isViewActualVisible() {
        return mViewActualVisible;
    }
    
    /**
     * 按下返回键
     *
     * @return {@code true} 消费此次返回事件，{@code false} 不消费此次返回事件
     */
    public boolean onBackPressed() {
        return false;
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
        List<Fragment> childFragment = getChildFragment();
        for (int i = childFragment.size() - 1; i >= 0; i--) {
            Fragment fragment = childFragment.get(i);
            if (!(fragment instanceof BaseFragment)) {
                continue;
            }
            
            BaseFragment baseFragment = (BaseFragment) fragment;
            if (!baseFragment.isViewActualVisible()) {
                continue;
            }
            
            if (type == 0 && baseFragment.onBackPressed()) {
                return true;
            } else if (type == 1 && baseFragment.onKeyDown(keyCode, event)) {
                return true;
            } else if (type == 2 && baseFragment.dispatchKeyEvent(event)) {
                return true;
            }
        }
        
        return false;
    }
    
    private List<Fragment> getChildFragment() {
        FragmentManager manager = getChildFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        Fragment backStackTopFragment = null;
        if (backStackEntryCount > 0) {
            FragmentManager.BackStackEntry backStackEntryAt
                = manager.getBackStackEntryAt(backStackEntryCount - 1);
            backStackTopFragment = manager.findFragmentByTag(backStackEntryAt.getName());
        }
        
        // manager.getFragments()返回数据可能包含回退栈中栈顶数据,但是这只在栈顶数据是刚刚新加入的情况
        // 如果是从回退栈中弹出，立即使用manager.getFragments()获取是不包含栈顶数据的，但是刚弹出的界面
        // 可能对于用户是可见状态，因此如果不包含，需要加上栈顶Fragment
        List<Fragment> fragments = manager.getFragments();
        if (backStackTopFragment != null && !fragments.contains(backStackTopFragment)) {
            fragments.add(backStackTopFragment);
        }
        
        return fragments;
    }
    
    @Override
    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
