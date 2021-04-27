package me.zhouzhuo810.magpiex.ui.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.rxjava3.disposables.Disposable;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.event.CloseAllActEvent;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.LoadingDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpiex.ui.fgm.BaseFragment;
import me.zhouzhuo810.magpiex.utils.ActivityUtil;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;

/**
 * @author zhouzhuo810
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {
    
    private ListDialog listDialog;
    private BottomSheetDialog bsDialog;
    private LoadingDialog loadingDialog;
    private TwoBtnTextDialog oneBtnTextDialog;
    private TwoBtnTextDialog twoBtnTextDialog;
    private TwoBtnEditDialog twoBtnEditDialog;
    private OneBtnProgressDialog progressDialog;
    
    /**
     * 上下文对象
     */
    protected Context mContext;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = this;
    
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            return;
        }
    
        EventBus.getDefault().register(this);
        
        setContentView(layoutId);
        
        SimpleUtil.resetScale(this);
        
        SimpleUtil.scaleView(getDecorView());
        
        if (!shouldNotInvokeInitMethods(savedInstanceState)) {
            initView(savedInstanceState);
            initData();
            initEvent();
        }
    }
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
    
    @Override
    public boolean useSysFinishAnim() {
        return false;
    }
    
    @Override
    public View getDecorView() {
        return getWindow().getDecorView();
    }
    
    @Override
    protected void onResume() {
        // FIXME by 周卓 时间：12/2/20 11:23 AM 防止调用Activity#isTopTask()方法时某些手机奔溃
        try {
            super.onResume();
        } catch (Exception ignored) {
        }
        String simpleName = getClass().getSimpleName();
        SpUtil.putString(Cons.SP_KEY_OF_CURRENT_ACTIVITY_OR_FRAGMENT_NAME, simpleName);
    }
    
    /**
     * 跳转到目标Activity
     *
     * @param clazz 目标界面
     */
    @Override
    public void startAct(Class<? extends Activity> clazz) {
        startActWithIntent(new Intent(this, clazz));
    }
    
    @Override
    public void startActForResult(Class<? extends Activity> clazz, int requestCode) {
        startActWithIntentForResult(new Intent(this, clazz), requestCode);
    }
    
    @Override
    public void startActShared(Class<? extends Activity> clazz, final View... sharedElements) {
        startActWithIntentShared(new Intent(this, clazz), sharedElements);
    }
    
    @Override
    public void startActWithIntent(Intent intent) {
        startActWithIntent(intent, false);
    }
    
    @Override
    public void startActWithIntentShared(Intent intent, final View... sharedElements) {
        try {
            startActivity(intent, ActivityUtil.getOptionsBundle(this, sharedElements));
        } catch (Exception e) {
            // FIXME by 周卓 时间：12/2/20 11:23 AM 防止调用Activity#isTopTask()方法时某些手机奔溃
            startActivity(intent);
            overridePendingTransition(openInAnimation(), openOutAnimation());
        }
    }
    
    @Override
    public void startActWithIntent(Intent intent, boolean defaultAnim) {
        if (defaultAnim) {
            startActivity(intent);
        } else {
            try {
                startActivity(intent, ActivityUtil.getOptionsBundle(this, openInAnimation(), openOutAnimation()));
            } catch (Exception e) {
                // FIXME by 周卓 时间：12/2/20 11:23 AM 防止调用Activity#isTopTask()方法时某些手机奔溃
                startActivity(intent);
                overridePendingTransition(openInAnimation(), openOutAnimation());
            }
        }
    }
    
    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode) {
        startActWithIntentForResult(intent, requestCode, false);
    }
    
    @SuppressLint("RestrictedApi")
    @Override
    public void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim) {
        if (defaultAnim) {
            startActivityForResult(intent, requestCode);
        } else {
            try {
                startActivityForResult(intent, requestCode, ActivityUtil.getOptionsBundle(this, openInAnimation(), openOutAnimation()));
            } catch (Exception e) {
                // FIXME by 周卓 时间：12/2/20 11:23 AM 防止调用Activity#isTopTask()方法时某些手机奔溃
                startActivityForResult(intent, requestCode);
                overridePendingTransition(openInAnimation(), openOutAnimation());
            }
        }
    }
    
    @Override
    public void restart() {
        recreate();
    }
    
    @Override
    public void closeAct() {
        closeAct(false);
    }
    
    @Override
    public void closeActWithOutAnim() {
        finish();
        overridePendingTransition(0, 0);
    }
    
    
    @Override
    public void onBackPressed() {
        if (useSysFinishAnim()) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
            overridePendingTransition(closeInAnimation(), closeOutAnimation());
        }
    }
    
    @Override
    public void closeAct(boolean defaultAnimation) {
        if (defaultAnimation) {
            finish();
        } else {
            finish();
            overridePendingTransition(closeInAnimation(), closeOutAnimation());
        }
    }
    
    @Override
    public void closeAllAct() {
        closeAllAct(false);
    }
    
    @Override
    public void closeAllAct(boolean defaultAnimation) {
        EventBus.getDefault().post(new CloseAllActEvent(defaultAnimation));
    }
    
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }
    
    @Override
    public int closeInAnimation() {
        return R.anim.mp_slide_in_left;
    }
    
    @Override
    public int closeOutAnimation() {
        return R.anim.mp_side_out_right;
    }
    
    @Override
    public void showLoadingDialog(CharSequence msg) {
        showLoadingDialog(null, msg);
    }
    
    @Override
    public void showLoadingDialog(CharSequence title, CharSequence msg) {
        showLoadingDialog(title, msg, false, null);
    }
    
    @Override
    public void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener listener) {
        showLoadingDialog(title, msg, cancelable, false, listener);
    }
    
    @Override
    public void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable) {
        showLoadingDialog(title, msg, cancelable, false, null);
    }
    
    @Override
    public void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, boolean iosStyle) {
        showLoadingDialog(title, msg, cancelable, iosStyle, null);
    }
    
    @Override
    public void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener) {
        if (loadingDialog != null && loadingDialog.isLoading()) {
            loadingDialog.setCancelable(cancelable);
            loadingDialog.setTitle(title)
                .setMsg(msg)
                .setIosStyle(iosStyle)
                .setOnDismissListener(onDismissListener)
                .update();
        } else {
            hideLoadingDialog();
            loadingDialog = new LoadingDialog();
            loadingDialog.setTitle(title)
                .setMsg(msg)
                .setLandscape(isLandscapeDialog())
                .setIosStyle(iosStyle)
                .setOnDismissListener(onDismissListener)
                .setCancelable(cancelable);
            loadingDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
        }
    }
    
    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }
    
    /**
     * LoadingDialog是否正在加载
     *
     * @return 是否
     */
    public boolean isLoading() {
        return loadingDialog != null && loadingDialog.isLoading();
    }
    
    
    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismissDialog();
        }
    }
    
    @Override
    public void showOneBtnProgressDialog(CharSequence title, CharSequence msg, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, null, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(CharSequence title, CharSequence msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, false, onDismissListener, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, getOkText(), cancelable, onDismissListener, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(CharSequence title, CharSequence msg, CharSequence btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        showOneBtnProgressDialog(title, msg, btnString, cancelable, false, onDismissListener, onProgressListener);
    }
    
    @Override
    public void showOneBtnProgressDialog(CharSequence title, CharSequence msg, CharSequence btnString, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener) {
        progressDialog = new OneBtnProgressDialog();
        progressDialog.setTitle(title)
            .setMsg(msg)
            .setBtnText(btnString)
            .setFromHtml(fromHtml)
            .setLandscape(isLandscapeDialog())
            .setOnDismissListener(onDismissListener)
            .setProgressListener(onProgressListener)
            .setCancelable(cancelable);
        progressDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideOneBtnProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismissDialog();
        }
    }
    
    @Override
    public void showOneBtnTextDialog(CharSequence title, CharSequence msg, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick) {
        showOneBtnTextDialog(title, msg, getOkText(), true, null, onOneBtnTextClick);
    }
    
    @Override
    public void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick) {
        showOneBtnTextDialog(title, msg, btnText, cancelable, null, onOneBtnTextClick);
    }
    
    @Override
    public void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick) {
        showOneBtnTextDialog(title, msg, btnText, cancelable, false, onDismissListener, onOneBtnTextClick);
    }
    
    @Override
    public void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick) {
        oneBtnTextDialog = new TwoBtnTextDialog();
        oneBtnTextDialog.setTitle(title)
            .setMsg(msg)
            .setLandscape(isLandscapeDialog())
            .setOneBtn(true)
            .setFromHtml(fromHtml)
            .setLeftText(btnText)
            .setOnDismissListener(onDismissListener)
            .setOnOneBtnClickListener(onOneBtnTextClick)
            .setCancelable(cancelable);
        oneBtnTextDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideOneBtnEditDialog() {
        if (oneBtnTextDialog != null) {
            oneBtnTextDialog.dismissDialog();
        }
    }
    
    @Override
    public void showTwoBtnTextDialog(CharSequence title, CharSequence msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, false, onTwoBtnClick);
    }
    
    
    @Override
    public void showTwoBtnTextDialog(CharSequence title, CharSequence msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, cancelable, null, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, getCancelText(), getOkText(), cancelable, onDismissListener, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(CharSequence title, CharSequence msg, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        showTwoBtnTextDialog(title, msg, leftBtnString, rightBtnString, cancelable, false, onDismissListener, onTwoBtnClick);
    }
    
    @Override
    public void showTwoBtnTextDialog(CharSequence title, CharSequence msg, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick) {
        hideTwoBtnTextDialog();
        twoBtnTextDialog = new TwoBtnTextDialog();
        twoBtnTextDialog.setTitle(title)
            .setMsg(msg)
            .setLandscape(isLandscapeDialog())
            .setLeftText(leftBtnString)
            .setRightText(rightBtnString)
            .setFromHtml(fromHtml)
            .setOnDismissListener(onDismissListener)
            .setOnTwoBtnClickListener(onTwoBtnClick)
            .setCancelable(cancelable);
        twoBtnTextDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideTwoBtnTextDialog() {
        if (twoBtnTextDialog != null) {
            twoBtnTextDialog.dismissDialog();
        }
    }
    
    @Override
    public void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, boolean cancelable, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, cancelable, null, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, getCancelText(), getOkText(), cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, int inputType, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, inputType, getCancelText(), getOkText(), cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        showTwoBtnEditDialog(title, msg, hint, InputType.TYPE_CLASS_TEXT, leftBtnString, rightBtnString, cancelable, onDismissListener, onTwoBtnEditClick);
    }
    
    @Override
    public void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, int inputType, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick) {
        hideTwoBtnEditDialog();
        twoBtnEditDialog = new TwoBtnEditDialog();
        twoBtnEditDialog.setTitle(title)
            .setMsg(msg)
            .setHint(hint)
            .setLandscape(isLandscapeDialog())
            .setInputType(inputType)
            .setLeftText(leftBtnString)
            .setRightText(rightBtnString)
            .setOnDismissListener(onDismissListener)
            .setOnTwoBtnClickListener(onTwoBtnEditClick)
            .setCancelable(cancelable);
        twoBtnEditDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideTwoBtnEditDialog() {
        if (twoBtnEditDialog != null) {
            twoBtnEditDialog.dismissDialog();
        }
    }
    
    @Override
    public void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(null, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, CollectionUtil.stringToList(items), alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, cancelable, null, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        showListDialog(title, items, false, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public void showListDialog(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick) {
        hideListDialog();
        listDialog = new ListDialog();
        listDialog
            .setOnItemClick(onItemClick)
            .setOnDismissListener(onDismissListener)
            .setAlignLeft(alignLeft)
            .setLandscape(isLandscapeDialog())
            .setTitle(title)
            .setItems(items)
            .setCancelable(cancelable);
        listDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideListDialog() {
        if (listDialog != null) {
            listDialog.dismissDialog();
        }
    }
    
    @Override
    public void showBottomSheet(CharSequence title, List<String> items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, items, false, cancelable, onItemClick);
    }
    
    @Override
    public void showBottomSheet(CharSequence title, String[] items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), false, cancelable, onItemClick);
    }
    
    @Override
    public void showBottomSheet(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, items, alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showBottomSheet(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), alignLeft, cancelable, null, onItemClick);
    }
    
    @Override
    public void showBottomSheet(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick) {
        showBottomSheet(title, CollectionUtil.stringToList(items), alignLeft, cancelable, onDismissListener, onItemClick);
    }
    
    @Override
    public void showBottomSheet(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick) {
        hideBottomSheet();
        bsDialog = new BottomSheetDialog();
        bsDialog
            .setTitle(title)
            .setOnItemClick(onItemClick)
            .setOnDismissListener(onDismissListener)
            .setItems(items)
            .setLandscape(isLandscapeDialog())
            .setAlignLeft(alignLeft);
        bsDialog.setCancelable(cancelable);
        bsDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    
    @Override
    public void hideBottomSheet() {
        if (bsDialog != null) {
            bsDialog.dismissDialog();
        }
    }
    
    @Override
    public void refreshData(String... params) {
    
    }
    
    @Override
    public void loadMoreData(String... params) {
    
    }
    
    @Override
    public int openInAnimation() {
        return R.anim.mp_slide_in_right;
    }
    
    @Override
    public int openOutAnimation() {
        return R.anim.mp_side_out_left;
    }
    
    
    @Override
    public TextWatcher setEditImageListener(final EditText et, final ImageView iv) {
        if (et == null) {
            throw new NullPointerException("EditText should not be null.");
        }
        if (iv == null) {
            throw new NullPointerException("ImageView should not be null.");
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    iv.setVisibility(View.VISIBLE);
                } else {
                    iv.setVisibility(View.GONE);
                }
            }
        };
        et.addTextChangedListener(textWatcher);
        return textWatcher;
        
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        //如果支持多语言，才给切换语言
        if (shouldSupportMultiLanguage()) {
            Integer language = SpUtil.getInt(newBase, Cons.SP_KEY_OF_CHOOSED_LANGUAGE, -1);
            Context context = LanguageUtil.attachBaseContext(newBase, language);
            final Configuration configuration = context.getResources().getConfiguration();
            // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
            // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
            // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
            final ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,
                R.style.MagpieTheme_NoActionBar) {
                @Override
                public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                    if (overrideConfiguration != null) {
                        overrideConfiguration.setTo(configuration);
                    }
                    super.applyOverrideConfiguration(overrideConfiguration);
                }
            };
            super.attachBaseContext(wrappedContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }
    
    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        // 兼容androidX在部分手机切换语言失败问题
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
    
    @Override
    public <T extends BaseFragment> T findFragmentByClazzAsTag(Class<T> clazz) {
        if (getSupportFragmentManager() == null) {
            return null;
        }
        return (T) getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
    }
    
    @Override
    public <T extends BaseFragment> T findFragmentByTag(String tag) {
        if (getSupportFragmentManager() == null) {
            return null;
        }
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }
    
    @Override
    public <T extends BaseFragment> void replaceFragment(@IdRes int containerId, Class<T> clazz, T fragment, Bundle bundle) {
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
        }
        fragment.setArguments(bundle);
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commitNow();
        }
    }
    
    @Override
    public <T extends BaseFragment> T addOrShowFragment(@IdRes int containerId, Class<T> clazz, Bundle bundle) {
        T fragment = findFragmentByClazzAsTag(clazz);
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
            fragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.add(containerId, fragment, clazz.getSimpleName())
                    .show(fragment)
                    .commitNow();
            }
        } else {
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.show(fragment)
                    .commitNow();
            }
        }
        return fragment;
    }
    
    @Override
    public <T extends BaseFragment> T addOrShowFragmentCustomTag(@IdRes int containerId, Class<T> clazz, String tag, Bundle bundle) {
        T fragment = findFragmentByTag(tag);
        if (fragment == null) {
            fragment = T.newInstance(clazz, bundle);
            fragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.add(containerId, fragment, tag)
                    .show(fragment)
                    .commitNow();
            }
        } else {
            if (getSupportFragmentManager() != null) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (fragments != null) {
                    for (Fragment fgm : fragments) {
                        if (fgm != null) {
                            if (fgm.isAdded() && !fgm.isHidden()) {
                                transaction.hide(fgm);
                            }
                        }
                    }
                }
                transaction.show(fragment)
                    .commitNow();
            }
        }
        return fragment;
    }
    
    @Override
    public <T extends BaseFragment> void hideFragment(T fragment) {
        if (fragment != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                    .hide(fragment)
                    .commitNow();
            }
        }
    }
    
    @Override
    public <T extends BaseFragment> void hideFragmentByClass(Class<T> clazz) {
        T fragment = findFragmentByClazzAsTag(clazz);
        if (fragment != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                    .hide(fragment)
                    .commitNow();
            }
        }
    }
    
    @Override
    public void hideFragmentByTag(String tag) {
        Fragment fgm = findFragmentByTag(tag);
        if (fgm != null) {
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                    .hide(fgm)
                    .commitNow();
            }
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseAllActEvent(CloseAllActEvent event) {
        if (event.isDefaultAnim()) {
            finish();
        } else {
            closeActWithOutAnim();
        }
    }
    
    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        hideListDialog();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    
    @Override
    public void cancelDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    
    @Override
    public String getOkText() {
        return getString(R.string.magpie_ok_text);
    }
    
    @Override
    public String getCancelText() {
        return getString(R.string.magpie_cancel_text);
    }
    
    @Override
    public boolean isLandscapeDialog() {
        return false;
    }
}
