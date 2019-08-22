package me.zhouzhuo810.magpiex.ui.fgm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import androidx.fragment.app.Fragment;
import io.reactivex.disposables.Disposable;
import me.zhouzhuo810.magpiex.ui.act.IBaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;

public interface IBaseFragment {
    
    /**
     * 获取布局的id
     */
    public int getLayoutId();
    
    public <T extends View> T findViewById(@IdRes int id);
    
    public void initView(@Nullable Bundle savedInstanceState);
    
    public void initData();
    
    /**
     * 使用ViewPager+Fragment时使用此方法实现懒加载
     */
    public void lazyLoadData();
    
    /**
     * 是否需要调用{@link #lazyLoadData()}
     */
    public boolean isNeedLazyLoad();
    
    public void initEvent();
    
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState);
    
    public IBaseActivity getBaseAct();
    
    public void startAct(Class<? extends Activity> clazz);
    
    public void startActForResult(Class<? extends Activity> clazz, int requestCode);
    
    public void startActWithIntent(Intent intent);
    
    public void startActWithIntent(Intent intent, boolean defaultAnim);
    
    public void startActWithIntentForResult(Intent intent, int requestCode);
    
    public void startActWithIntentForResult(Intent intent, int requestCode, boolean defaultAnim);
    
    public void closeAct();
    
    public void closeAct(boolean defaultAnimation);
    
    public void closeAllAct();
    
    public void overridePendingTransition(int enterAnim, int exitAnim);
    
    public void showLoadingDialog(String msg);
    
    public void showLoadingDialog(String title, String msg);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener listener);
    
    public void showLoadingDialog(String title, String msg, boolean cancelable, boolean iosStyle, DialogInterface.OnDismissListener onDismissListener);
    
    public void hideLoadingDialog();
    
    public void showOneBtnProgressDialog(String title, String msg, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void showOneBtnProgressDialog(String title, String msg, String btnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, OneBtnProgressDialog.OnProgressListener onProgressListener);
    
    public void hideOneBtnProgressDialog();
    
    public void showTwoBtnTextDialog(String title, String msg, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void showTwoBtnTextDialog(String title, String msg, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnTextDialog.OnTwoBtnTextClick onTwoBtnClick);
    
    public void hideTwoBtnTextDialog();
    
    public void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    public void showTwoBtnEditDialog(String title, String msg, String hint, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    public void showTwoBtnEditDialog(String title, String msg, String hint, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    public void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    public void showTwoBtnEditDialog(String title, String msg, String hint, int inputType, String leftBtnString, String rightBtnString, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, TwoBtnEditDialog.OnTwoBtnEditClick onTwoBtnEditClick);
    
    public void hideTwoBtnEditDialog();
    
    public void showListDialog(String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, String[] items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, String[] items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean cancelable, ListDialog.OnItemClick onItemClick);
    
    public void showListDialog(String title, List<String> items, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
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
    public void showListDialog(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, ListDialog.OnItemClick onItemClick);
    
    public void hideListDialog();
    
    public void showBottomSheet(String title, String[] items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    public void showBottomSheet(String title, String[] items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    public void showBottomSheet(String title, List<String> items, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    public void showBottomSheet(String title, List<String> items, boolean alignLeft, boolean cancelable, BottomSheetDialog.OnItemClick onItemClick);
    
    public void showBottomSheet(String title, String[] items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    public void showBottomSheet(String title, List<String> items, boolean alignLeft, boolean cancelable, DialogInterface.OnDismissListener onDismissListener, BottomSheetDialog.OnItemClick onItemClick);
    
    public void hideBottomSheet();
    
    public void refreshData(String... params);
    
    public void loadMoreData(String... params);
    
    public TextWatcher setEditImageListener(EditText et, ImageView iv);
    
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
    
    public Fragment findFragmentByTag(String tag);
    
    public void cancelDisposable(Disposable disposable);
}
