package me.zhouzhuo810.magpiex.ui.act;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpiex.ui.fgm.BaseFragment;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;

public interface IBaseActivity {
    
    /**
     * 是否使用系统默认的finish动画
     *
     * @return 默认false，即使用框架提供的动画
     */
    boolean useSysFinishAnim();
    
    /**
     * 屏幕适配需要返回getWindow().getDecorView();
     *
     * @return {@link Activity#getWindow()#getDecorView()}
     */
    View getDecorView();
    
    void initView(@Nullable Bundle savedInstanceState);
    
    /**
     * 获取布局的id
     *
     * @return layoutId
     */
    int getLayoutId();
    
    void initData();
    
    void initEvent();
    
    
    
    /**
     * 是否支持多语言
     *
     * @return true/false, 默认返回false
     * <p>
     * 如果返回false，表示您的app不需要支持多语言；
     * <p>
     * 如果返回true，表示您的app需要支持多语言
     * <p>
     * 可以使用{@link Application#onCreate()}中调用
     * {@link LanguageUtil#setGlobalLanguage(Integer)}
     * 方法设置默认语言
     * <p>
     */
    boolean shouldSupportMultiLanguage();
    
    
    /**
     * 判断是否调用initView、initData、initEvent方法。
     *
     * @return 是否
     */
    boolean shouldNotInvokeInitMethods(Bundle savedInstanceState);
    
    void startAct(Class<? extends Activity> clazz);
    
    void startActForResult(Class<? extends Activity> clazz, int requestCode);
    
    void startActShared(Class<? extends Activity> clazz, final View... sharedElements);
    
    void startActWithIntent(Intent intent);
    
    void startActWithIntent(Intent intent, boolean defaultAnim);
    
    void startActWithIntentShared(Intent intent, final View... sharedElements);
    
    void startActWithIntentForResult(Intent intent, int requestCode);
    
    void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim);
    
    void restart();
    
    void closeAct();
    
    void closeAct(boolean defaultAnimation);
    
    void closeActWithOutAnim();
    
    void closeAllAct(boolean defaultAnimation);
    
    void closeAllAct();
    
    void overridePendingTransition(int enterAnim, int exitAnim);
    
    /**
     * 启动Activity进入动画
     *
     * @return resId
     */
    int openInAnimation();
    
    /**
     * 启动Activity退出动画
     *
     * @return resId
     */
    int openOutAnimation();
    
    /**
     * 关闭Activity进入动画
     *
     * @return resId
     */
    int closeInAnimation();
    
    /**
     * 关闭Activity退出动画
     *
     * @return resId
     */
    int closeOutAnimation();
    
    void showLoadingDialog(CharSequence msg);
    
    void showLoadingDialog(CharSequence title, CharSequence msg);
    
    void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable);
    
    void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, boolean iosStyle);
    
    void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener listener);
    
    void showLoadingDialog(CharSequence title, CharSequence msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener);
    
    void hideLoadingDialog();
    
    void showOneBtnProgressDialog(CharSequence title, CharSequence msg, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(CharSequence title, CharSequence msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(CharSequence title, CharSequence msg, CharSequence btnString, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(CharSequence title, CharSequence msg, CharSequence btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void hideOneBtnProgressDialog();
    
    void showOneBtnTextDialog(CharSequence title, CharSequence msg, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick);
    
    void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick);
    
    void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick);
    
    void showOneBtnTextDialog(CharSequence title, CharSequence msg, CharSequence btnText, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnOneBtnTextClick onOneBtnTextClick);
    
    void hideOneBtnEditDialog();
    
    void showTwoBtnTextDialog(CharSequence title, CharSequence msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(CharSequence title, CharSequence msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(CharSequence title, CharSequence msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(CharSequence title, CharSequence msg, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(CharSequence title, CharSequence msg, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, boolean fromHtml, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void hideTwoBtnTextDialog();
    
    void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, boolean cancelable, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, int inputType, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(CharSequence title, CharSequence msg, CharSequence hint, int inputType, CharSequence leftBtnString, CharSequence rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void hideTwoBtnEditDialog();
    
    void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(CharSequence title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(CharSequence title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(CharSequence title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    /**
     * 显示列表对话框
     *
     * @param title             对话框标题，传null表示不显示
     * @param items             列表数据集合
     * @param alignLeft         是否左对齐，否则居中对齐
     * @param cancelable        是否可以点击空白处或点返回键取消
     * @param onDismissListener 对话框消失回调
     * @param onItemClick       对话框点击回调
     */
    void showListDialog(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    void hideListDialog();
    
    void showBottomSheet(CharSequence title, String[] items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(CharSequence title, List<String> items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(CharSequence title, String[] items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(CharSequence title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    void hideBottomSheet();
    
    void refreshData(String... params);
    
    void loadMoreData(String... params);
    
    TextWatcher setEditImageListener(EditText et, ImageView iv);
    
    <T extends BaseFragment> T findFragmentByTag(String tag);
    
    <T extends BaseFragment> T findFragmentByClazzAsTag(Class<T> clazz);
    
    <T extends BaseFragment> void replaceFragment(@IdRes int containerId, Class<T> clazz, T fragment, Bundle bundle);
    
    /**
     * 添加或显示Fragment
     *
     * 使用clazz.setSimpleName()作为tag，必须保证同一个Activity中使用addOrShowFragment的Fragment的的类名不相同；
     * 如果存在相同的类名，请使用{@link BaseActivity#addOrShowFragmentCustomTag}
     *
     * @param containerId 容器id
     * @param clazz       Fragment类
     * @param bundle      参数
     * @param <T>         Fragment
     * @return Fragment实例
     */
    <T extends BaseFragment> T addOrShowFragment(@IdRes int containerId, Class<T> clazz, Bundle bundle);
    
    /**
     * 添加或显示Fragment
     *
     * 使用自定义Tag，可以使用{@link BaseActivity#findFragmentByTag(String)}查找Fragment实例。
     *
     * @param containerId 容器id
     * @param clazz       Fragment类
     * @param tag         自定义Tag
     * @param bundle      参数
     * @param <T>         Fragment
     * @return Fragment实例
     */
    <T extends BaseFragment> T addOrShowFragmentCustomTag(@IdRes int containerId, Class<T> clazz, String tag, Bundle bundle);
    
    <T extends BaseFragment> void hideFragment(T fragment);
    
    <T extends BaseFragment> void hideFragmentByClass(Class<T> clazz);
    
    void hideFragmentByTag(String tag);
    
    void cancelDisposable(Disposable disposable);
    
    /**
     * 是否横屏对话框
     *
     * @return 是否
     */
    boolean isLandscapeDialog();
    
    /**
     * 返回对话框确定文字
     * @return String
     */
    String getOkText();
    
    /**
     * 返回对话框取消文字
     * @return String
     */
    String getCancelText();
}
