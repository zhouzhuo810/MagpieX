package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import me.zhouzhuo810.magpiex.utils.thread.AsyncExecutor;

/**
 * 处理自动换行，修复原生自动换行由于全角半角符号问题导致换行位置错误Bug<br>
 * 注意：只支持{@link String}字符类型，设置了其它复杂类型，最后也是转换为String处理
 */
public class AutoSplitTextView extends androidx.appcompat.widget.AppCompatTextView {
    /**
     * 是否自己处理自动换行
     */
    private boolean mAutoSplit = true;
    
    /**
     * 是否需要再次绘制
     */
    private boolean mNeedDraw = false;
    
    /**
     * 当前展示的文本
     */
    private String mText;
    
    /**
     * 启动线程来分割文字的最小长度，大于这个值开始启用线程
     */
    public static int MIN_TEXT_LENGTH_TO_START_THREAD = 1000;
    
    public AutoSplitTextView(Context context) {super(context);}
    
    public AutoSplitTextView(Context context, AttributeSet attrs) {super(context, attrs); }
    
    public AutoSplitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setAutoSplitEnabled(boolean enabled) {
        mAutoSplit = enabled;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 每次重新测绘时需要重新布局文本，如果界面大小发送变更，在此方法调用后即调用onSizeChanged，
        // 因此无需在onSizeChanged方法中调用reDrawText()，只需在此处调用即可
        reDrawText();
    }
    
    @Override
    public void setText(CharSequence text, BufferType type) {
        // 判断字符是否发生变更或变更后的内容是否已经绘制到界面
        // 从而减少处理字符换行逻辑的次数，提高性能
        if (text == null) {
            mNeedDraw = !TextUtils.isEmpty(mText);
        } else {
            mNeedDraw = !text.toString().equals(mText);
        }
        
        if (mNeedDraw) {
            mText = text == null ? null : text.toString();
            // 必须先设置一次，否则崩溃
            super.setText(text, type);
        }
    }
    
    /**
     * 重新绘制字符
     */
    private void reDrawText() {
        if (mAutoSplit && mNeedDraw) {
            mNeedDraw = false;
            if (TextUtils.isEmpty(mText)) {
                return;
            }
            
            final int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0 && height > 0) {
                if (mText.length() > MIN_TEXT_LENGTH_TO_START_THREAD) {
                    AsyncExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            final String newText = autoSplitText(mText, width);
                            if (!TextUtils.isEmpty(newText)) {
                                post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AutoSplitTextView.super.setText(newText, BufferType.NORMAL);
                                    }
                                });
                            }
                        }
                    });
                } else {
                    String newText = autoSplitText(mText, width);
                    if (!TextUtils.isEmpty(newText)) {
                        super.setText(newText, BufferType.NORMAL);
                    }
                }
            }
        }
    }
    
    /**
     * 在超出View宽度的文本后面加上"\n"符
     *
     * @param rawText 需要处理的文本
     * @param width   当前View的宽度
     * @return 处理后的文本
     */
    private String autoSplitText(String rawText, int width) {
        //原始文本
        final Paint tvPaint = getPaint();
        //paint,包含字体等信息
        final float tvWidth = width - getPaddingLeft() - getPaddingRight();
        //控件可用宽度
        // 将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        
        for (int i = 0; i < rawTextLines.length; i++) {
            if (i > 0) {
                sbNewText.append("\n");
            }
            
            String rawTextLine = rawTextLines[i];
            if (tvPaint.measureText(rawTextLine) < tvWidth) {
                //如果整行宽度在控件可用宽度之内,就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度,则按字符测量,在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth < tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
        }
        
        return sbNewText.toString();
    }
}
