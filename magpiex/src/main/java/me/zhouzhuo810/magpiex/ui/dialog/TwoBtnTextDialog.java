package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private OnOneBtnTextClick onOneBtnClickListener;
    private boolean landscape;
    private boolean oneBtn;
    private String title;
    private String msg;
    private String leftText;
    private String rightText;
    
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
     * 设置两个按钮点击监听
     *
     * @param onTwoBtnClickListener 监听
     * @return 自己
     */
    public TwoBtnTextDialog setOnTwoBtnClickListener(OnTwoBtnTextClick onTwoBtnClickListener) {
        this.onTwoBtnClick = onTwoBtnClickListener;
        return this;
    }
    
    /**
     * 设置一个按钮点击监听
     *
     * @param onOneBtnClickListener 监听
     * @return 自己
     */
    public TwoBtnTextDialog setOnOneBtnClickListener(OnOneBtnTextClick onOneBtnClickListener) {
        this.onOneBtnClickListener = onOneBtnClickListener;
        return this;
    }
    
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public TwoBtnTextDialog setLandscape(boolean landscape) {
        this.landscape = landscape;
        return this;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    
    /**
     * 是否一个按钮
     *
     * @param oneBtn 是否
     * @return 自己
     */
    public TwoBtnTextDialog setOneBtn(boolean oneBtn) {
        this.oneBtn = oneBtn;
        return this;
    }
    
    public boolean isOneBtn() {
        return oneBtn;
    }
    
    public TwoBtnTextDialog setLeftText(String leftBtnText) {
        this.leftText = leftBtnText;
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
        View rootView = inflater.inflate(landscape ? R.layout.layout_two_btn_text_dialog_land : R.layout.layout_two_btn_text_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return rootView;
        }
        ScreenAdapterUtil.getInstance().loadView(rootView);
        final TextView tvLeft = rootView.findViewById(R.id.tv_left);
        final TextView tvRight = rootView.findViewById(R.id.tv_right);
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        if (oneBtn) {
            tvRight.setVisibility(View.GONE);
            if (onOneBtnClickListener != null) {
                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onOneBtnClickListener != null) {
                            onOneBtnClickListener.onBtnClick(tvLeft);
                        }
                        dismissDialog();
                    }
                });
            }
        } else {
            tvRight.setVisibility(View.VISIBLE);
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
        }
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        final TextView tvMsg = rootView.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvMsg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lineCount = tvMsg.getLineCount();
                tvMsg.setGravity(lineCount > 1 ? Gravity.START | Gravity.CENTER_VERTICAL : Gravity.CENTER);
                tvMsg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        
        View line = rootView.findViewById(R.id.line_item);
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
        super.dismissAllowingStateLoss();
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
    
    public interface OnOneBtnTextClick {
        void onBtnClick(TextView v);
    }
}
