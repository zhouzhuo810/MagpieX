package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.KeyboardUtil;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

/**
 * 两个按钮输入对话框
 */
public class TwoBtnEditDialog extends DialogFragment {
    
    private int inputType = -1;
    private DialogInterface.OnDismissListener dismissListener;
    private OnTwoBtnEditClick onTwoBtnClick;
    private boolean landscape;
    private String title;
    private String hint;
    private String msg;
    private String leftText;
    private String rightText;
    private View rootView;
    
    
    /**
     * 设置输入框EditText输入值得类型
     * <p>
     * 参考 {@link EditText#setInputType(int)}
     * <p>
     * 常用值：
     * <ul>
     * <li>{@link InputType#TYPE_CLASS_TEXT}   输入文字</li>
     * <li>{@link InputType#TYPE_CLASS_NUMBER} 输入纯数字</li>
     * <li>{@link InputType#TYPE_NUMBER_FLAG_DECIMAL} 输入浮点数</li>
     * <li>{@link InputType#TYPE_CLASS_PHONE}  输入手机号</li>
     * <li>{@link InputType#TYPE_TEXT_VARIATION_PASSWORD} 输入密码</li>
     * </ul>
     *
     * @param type InputType
     * @return 自己
     */
    public TwoBtnEditDialog setInputType(int type) {
        this.inputType = type;
        return this;
    }
    
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public TwoBtnEditDialog setLandscape(boolean landscape) {
        this.landscape = landscape;
        return this;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    
    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public TwoBtnEditDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }
    
    /**
     * 设置按钮点击监听
     *
     * @param onTwoBtnClickListener 监听
     * @return 自己
     */
    public TwoBtnEditDialog setOnTwoBtnClickListener(OnTwoBtnEditClick onTwoBtnClickListener) {
        this.onTwoBtnClick = onTwoBtnClickListener;
        return this;
    }
    
    /**
     * 设置左边按钮的文字
     *
     * @param leftBtnText 文字
     * @return 自己
     */
    public TwoBtnEditDialog setLeftText(String leftBtnText) {
        this.leftText = leftBtnText;
        return this;
    }
    
    /**
     * 设置右边按钮的文字
     *
     * @param rightBtnText 文字
     * @return 自己
     */
    public TwoBtnEditDialog setRightText(String rightBtnText) {
        this.rightText = rightBtnText;
        return this;
    }
    
    /**
     * 设置输入框提示内容
     *
     * @param hint 提示内容
     * @return 自己
     */
    public TwoBtnEditDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }
    
    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public TwoBtnEditDialog setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * 设置消息内容
     *
     * @param msg 消息内容，为空则表示不需要消息内容
     * @return 自己
     */
    public TwoBtnEditDialog setMsg(String msg) {
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
        rootView = inflater.inflate(landscape ? R.layout.layout_two_btn_edit_dialog_land : R.layout.layout_two_btn_edit_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return rootView;
        }
        ScreenAdapterUtil.getInstance().loadView(rootView);
        final TextView tvLeft = rootView.findViewById(R.id.tv_left);
        final TextView tvRight = rootView.findViewById(R.id.tv_right);
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        final EditText etMsg = rootView.findViewById(R.id.et_msg);
        if (inputType != -1) {
            etMsg.setInputType(inputType);
        }
        etMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tvRight.performClick();
                    handled = true;
                }
                return handled;
            }
        });
        
        
        if (hint != null) {
            etMsg.setHint(hint);
        }
        if (msg != null) {
            etMsg.setText(msg);
            etMsg.setSelection(0, msg.length());
        }
        if (onTwoBtnClick != null) {
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTwoBtnClick != null) {
                        final String content = etMsg.getText().toString().trim();
                        onTwoBtnClick.onLeftClick(content);
                    }
                    dismissDialog();
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String content = etMsg.getText().toString().trim();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (onTwoBtnClick != null) {
                                onTwoBtnClick.onRightClick(content);
                            }
                        }
                    }, 300);
                    dismissDialog();
                }
            });
        }
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rootView != null) {
                    EditText etMsg = rootView.findViewById(R.id.et_msg);
                    KeyboardUtil.showSoftInput2(etMsg);
                }
            }
        }, 300);
    }
    
    
    /**
     * 注意,不要用super.dismiss(),bug 同上show()
     * super.onDismiss就没问题
     */
    public void dismissDialog() {
        if (rootView != null) {
            EditText etMsg = rootView.findViewById(R.id.et_msg);
            KeyboardUtil.hideSoftInput(etMsg);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TwoBtnEditDialog.super.dismissAllowingStateLoss();
            }
        }, 150);
    }
    
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
    
    public interface OnTwoBtnEditClick {
        void onLeftClick(String etContent);
        
        void onRightClick(String etContent);
    }
    
}
