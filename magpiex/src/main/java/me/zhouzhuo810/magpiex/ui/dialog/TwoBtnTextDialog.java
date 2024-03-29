package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
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
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.WebUtil;

/**
 * 两个按钮普通对话框
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:54 PM
 */
public class TwoBtnTextDialog extends DialogFragment {
    
    private DialogInterface.OnDismissListener dismissListener;
    private OnTwoBtnTextClick onTwoBtnClick;
    private OnOneBtnTextClick onOneBtnClickListener;
    private boolean landscape;
    private boolean oneBtn;
    private boolean fromHtml;
    private CharSequence title;
    private CharSequence msg;
    private CharSequence leftText;
    private CharSequence rightText;
    
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
    
    public TwoBtnTextDialog setLeftText(CharSequence leftBtnText) {
        this.leftText = leftBtnText;
        return this;
    }
    
    /**
     * 设置是否使用Html.formHtml()加载msg;
     *
     * @param fromHtml 是否
     * @return 自己
     */
    public TwoBtnTextDialog setFromHtml(boolean fromHtml) {
        this.fromHtml = fromHtml;
        return this;
    }
    
    public TwoBtnTextDialog setRightText(CharSequence rightBtnText) {
        this.rightText = rightBtnText;
        return this;
    }
    
    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public TwoBtnTextDialog setTitle(CharSequence title) {
        this.title = title;
        return this;
    }
    
    /**
     * 设置消息内容
     *
     * @param msg 消息内容，为空则表示不需要消息内容
     * @return 自己
     */
    public TwoBtnTextDialog setMsg(CharSequence msg) {
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
        View rootView = inflater.inflate(landscape ? R.layout.layout_two_btn_text_dialog_land : R.layout.layout_two_btn_text_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return rootView;
        }
        SimpleUtil.scaleView(rootView);
        initView(rootView);
        return rootView;
    }
    
    private void initView(View rootView) {
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
        if (fromHtml) {
            tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
            tvMsg.setText(WebUtil.fromHtml((String) msg));
            tvMsg.setClickable(true);
        } else {
            tvMsg.setText(msg);
        }
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
    }
    
    
    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
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
    public void onDismiss(@NonNull DialogInterface dialog) {
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
