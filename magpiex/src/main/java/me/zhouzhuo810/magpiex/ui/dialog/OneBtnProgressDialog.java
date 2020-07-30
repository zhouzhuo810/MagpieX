package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

/**
 * 单个按钮进度对话框
 */
public class OneBtnProgressDialog extends DialogFragment {

    private DialogInterface.OnDismissListener dismissListener;
    private OnProgressListener onProgressListener;
    private boolean landscape;
    private String title;
    private String msg;
    private String btnText;

    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public OneBtnProgressDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    /**
     * 设置进度改变和按钮点击监听
     *
     * @param onProgressListener 监听
     * @return 自己
     */
    public OneBtnProgressDialog setProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        return this;
    }
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public OneBtnProgressDialog setLandscape(boolean landscape) {
        this.landscape = landscape;
        return this;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    
    
    /**
     * 设置按钮文字
     *
     * @param btnText 按钮文字
     * @return 自己
     */
    public OneBtnProgressDialog setBtnText(String btnText) {
        this.btnText = btnText;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public OneBtnProgressDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置消息内容
     *
     * @param msg 消息内容，为空则表示不需要消息内容
     * @return 自己
     */
    public OneBtnProgressDialog setMsg(String msg) {
        this.msg = msg;
        return this;
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
            try {
                getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } catch (Exception ignored) {
            }
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
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(landscape ? R.layout.layout_one_btn_progress_dialog_land : R.layout.layout_one_btn_progress_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return rootView;
        }
        ScreenAdapterUtil.getInstance().loadView(rootView);
        ProgressBar pb = rootView.findViewById(R.id.pb);
        final TextView tvOk = rootView.findViewById(R.id.tv_right);
        tvOk.setText(btnText);
        if (onProgressListener != null) {
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProgressListener != null) {
                        onProgressListener.onBtnClick();
                    }
                    dismissDialog();
                }
            });
        }
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        TextView tvMsg = rootView.findViewById(R.id.tv_msg);
        if (onProgressListener != null) {
            onProgressListener.onStart(pb, tvMsg, tvOk);
        }
        View line = rootView.findViewById(R.id.line_item);
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
        return rootView;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 注意,不要用super.dismiss(),bug 同上show()
     * super.onDismiss就没问题
     */
    public void dismissDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            super.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    public interface OnProgressListener {

        void onStart(ProgressBar pb, TextView tvMsg, TextView tvOk);

        void onBtnClick();

    }
}
