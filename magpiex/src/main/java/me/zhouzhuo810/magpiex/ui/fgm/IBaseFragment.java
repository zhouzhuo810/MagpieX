package me.zhouzhuo810.magpiex.ui.fgm;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
import io.reactivex.rxjava3.disposables.Disposable;
import me.zhouzhuo810.magpiex.ui.act.IBaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;

public interface IBaseFragment {
    
    
    /**
     * 代替{@code rootView.findViewById()};
     * @param id viewId
     * @param <T> ViewType
     * @return View
     */
    <T extends View> T findViewById(@IdRes int id);
    
    /**
     * 初始化视图
     *
     * @param savedInstanceState Bundle
     */
    void initView(@Nullable Bundle savedInstanceState);
    
    /**
     * 获取布局的id
     */
    int getLayoutId();
    
    /**
     * 初始化数据
     */
    void initData();
    
    boolean isNeedLazyLoad();
    
    /**
     * 绑定事件
     */
    void initEvent();
    
    /**
     * 是否不调用initView，initData和initEvent，默认false
     *
     * @param savedInstanceState Bundle
     * @return 是否
     */
    boolean shouldNotInvokeInitMethods(Bundle savedInstanceState);
    
    
    /**
     * 是否不屏幕适配当前界面，注意，仅对ContentView生效，不包括RV，LV等
     *
     * @return 是否
     */
    boolean shouldNotScreenAdapt();
    
    IBaseActivity getBaseAct();
    
    void startAct(Class<? extends Activity> clazz);
    
    void startActForResult(Class<? extends Activity> clazz, int requestCode);
    
    void startActWithIntent(Intent intent);
    
    void startActWithIntent(Intent intent, boolean defaultAnim);
    
    void startActWithIntentForResult(Intent intent, int requestCode);
    
    void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim);
    
    void closeAct();
    
    void closeAct(boolean defaultAnimation);
    
    /**
     * 关闭已打开的所有Activity
     */
    void closeAllAct();
    
    void overridePendingTransition(int enterAnim, int exitAnim);
    
    void showLoadingDialog(String msg);
    
    void showLoadingDialog(String title, String msg);
    
    void showLoadingDialog(String title, String msg, boolean cancelable);
    
    void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle);
    
    void showLoadingDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener listener);
    
    void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener);
    
    void hideLoadingDialog();
    
    void showOneBtnProgressDialog(String title, String msg, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(String title, String msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void showOneBtnProgressDialog(String title, String msg, String btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    void hideOneBtnProgressDialog();
    
    void showTwoBtnTextDialog(String title, String msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(String title, String msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void showTwoBtnTextDialog(String title, String msg, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    void hideTwoBtnTextDialog();
    
    void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(String title, String msg, String hint, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    void hideTwoBtnEditDialog();
    
    void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(String title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(String title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(String title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    void showListDialog(String title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
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
    void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    void hideListDialog();
    
    void showBottomSheet(String[] items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(String[] items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(List<String> items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(List<String> items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(String[] items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    void showBottomSheet(List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    void hideBottomSheet();
    
    void refreshData(String... params);
    
    void loadMoreData(String... params);
    
    TextWatcher setEditImageListener(EditText et, ImageView iv);
    
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
    
    Fragment findFragmentByTag(String tag);
    
    void cancelDisposable(Disposable disposable);
}
