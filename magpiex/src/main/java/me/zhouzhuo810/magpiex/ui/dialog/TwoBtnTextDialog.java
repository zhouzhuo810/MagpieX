package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

/**
 * 两个按钮普通对话框
 */
public class TwoBtnTextDialog extends DialogFragment {

    private DialogInterface.OnDismissListener dismissListener;
    private OnTwoBtnTextClick onTwoBtnClick;
    private String title;
    private String msg;
    private String leftText;
    private String rightText;
    private int gravity = Gravity.CENTER;

    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public TwoBtnTextDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    /**
     * 设置按钮点击监听
     *
     * @param onTwoBtnClickListener 监听
     * @return 自己
     */
    public TwoBtnTextDialog setOnTwoBtnClickListener(OnTwoBtnTextClick onTwoBtnClickListener) {
        this.onTwoBtnClick = onTwoBtnClickListener;
        return this;
    }

    public TwoBtnTextDialog setLeftText(String leftBtnText) {
        this.leftText = leftBtnText;
        return this;
    }
    
    public TwoBtnTextDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public TwoBtnTextDialog setRightText(String rightBtnText) {
        this.rightText = rightBtnText;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public TwoBtnTextDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置消息内容
     *
     * @param msg 消息内容，为空则表示不需要消息内容
     * @return 自己
     */
    public TwoBtnTextDialog setMsg(String msg) {
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
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(dm.widthPixels * 4 / 5, getDialog().getWindow().getAttributes().height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.layout_two_btn_text_dialog, container, false);
        ScreenAdapterUtil.getInstance().loadView(rootView);
        final TextView tvLeft = rootView.findViewById(R.id.tv_left);
        final TextView tvRight = rootView.findViewById(R.id.tv_right);
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        if (onTwoBtnClick != null) {
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTwoBtnClick != null) {
                        onTwoBtnClick.onLeftClick(tvLeft);
                    }
                    dismissDialog();
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTwoBtnClick != null) {
                        onTwoBtnClick.onRightClick(tvRight);
                    }
                    dismissDialog();
                }
            });
        }
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        TextView tvMsg = rootView.findViewById(R.id.tv_msg);
        tvMsg.setGravity(gravity);
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

    public interface OnTwoBtnTextClick {
        void onLeftClick(TextView v);

        void onRightClick(TextView v);
    }
}
