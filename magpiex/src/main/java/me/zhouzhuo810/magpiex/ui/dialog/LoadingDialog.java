package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;


/**
 * 加载对话框
 */
public class LoadingDialog extends DialogFragment {
    
    private DialogInterface.OnDismissListener dismissListener;
    private String title;
    private String msg;
    /**
     * 是否使用菊花加载
     */
    private boolean iosStyle;
    private boolean isLoading;
    private boolean landscape;
    private View mRootView;
    
    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public LoadingDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }
    
    /**
     * 是否使用iOS样式的加载框
     *
     * @param iosStyle 是否
     * @return 自己
     */
    public LoadingDialog setIosStyle(boolean iosStyle) {
        this.iosStyle = iosStyle;
        return this;
    }
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public LoadingDialog setLandscape(boolean landscape) {
        this.landscape = landscape;
        return this;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public LoadingDialog setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * 设置消息内容
     *
     * @param msg 消息内容，为空则表示不需要消息内容
     * @return 自己
     */
    public LoadingDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    
    /**
     * 如果想动态改变对话框内容而不关闭对话框，调用此方法
     */
    public void update() {
        if (mRootView != null) {
            ProgressBar pb = mRootView.findViewById(R.id.pb_loading);
            if (iosStyle) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (getActivity() != null) {
                        pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, getActivity().getTheme()));
                    } else {
                        pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, null));
                    }
                } else {
                    pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, null));
                }
            }
            TextView tvTitle = mRootView.findViewById(R.id.tv_title);
            TextView tvMsg = mRootView.findViewById(R.id.tv_msg);
            View line = mRootView.findViewById(R.id.line_item);
            tvMsg.setText(msg);
            if (TextUtils.isEmpty(title)) {
                line.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                tvTitle.setText("");
            } else {
                line.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            }
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (getDialog() != null && getDialog().getWindow() != null) {
            if (landscape) {
                getDialog().getWindow().setLayout(dm.widthPixels * 2 / 5, getDialog().getWindow().getAttributes().height);
            } else {
                getDialog().getWindow().setLayout(dm.widthPixels * 4 / 5, getDialog().getWindow().getAttributes().height);
            }
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //添加这一行
        if (getDialog() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        mRootView = inflater.inflate(landscape ? R.layout.layout_loading_dialog_land : R.layout.layout_loading_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return mRootView;
        }
        ScreenAdapterUtil.getInstance().loadView(mRootView);
        ProgressBar pb = mRootView.findViewById(R.id.pb_loading);
        if (iosStyle) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (getActivity() != null) {
                    pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, getActivity().getTheme()));
                } else {
                    pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, null));
                }
            } else {
                pb.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.loading_ios_anim, null));
            }
        }
        TextView tvTitle = mRootView.findViewById(R.id.tv_title);
        TextView tvMsg = mRootView.findViewById(R.id.tv_msg);
        View line = mRootView.findViewById(R.id.line_item);
        tvMsg.setText(msg);
        if (TextUtils.isEmpty(title)) {
            line.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvTitle.setText("");
        } else {
            line.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        return mRootView;
    }
    
    
    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            super.show(manager, tag);
            isLoading = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isLoading() {
        return isLoading;
    }
    
    /**
     * 注意,不要用super.dismiss(),bug 同上show()
     * super.onDismiss就没问题
     */
    public void dismissDialog() {
        isLoading = false;
        super.dismissAllowingStateLoss();
    }
    
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isLoading = false;
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
