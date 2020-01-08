package me.zhouzhuo810.magpiex.ui.widget.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;

import me.zhouzhuo810.magpiex.ui.widget.ShineTextView;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;


/**
 *
 */
@SuppressLint("ViewConstructor")
public class MapTextView extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int EMPTY = 2;
    
    private int mWidth, mHigh, mType;
    
    private ShineTextView mTextView;
    
    private int shiningDuration;
    private int borderWidth;
    private int textSize;
    
    public MapTextView(Context context, int width, int high, int textSize, int borderWidth, int shiningDuration) {
        super(context);
        this.mWidth = width;
        this.mHigh = high;
        this.textSize = textSize;
        this.mType = EMPTY;
        this.borderWidth = borderWidth;
        this.shiningDuration = shiningDuration;
        initEmptyChildView();
    }
    
    public MapTextView(Context context, int width, int high, int type, int textSize, int borderWidth, int shiningDuration) {
        super(context);
        this.mWidth = width;
        this.mHigh = high;
        this.mType = type;
        this.textSize = textSize;
        this.borderWidth = borderWidth;
        this.shiningDuration = shiningDuration;
        initChildView();
    }
    
    private void initChildView() {
        mTextView = new ShineTextView(getContext(), null);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setDurationWithSeconds(shiningDuration);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapterUtil.getInstance().getScaledValue(textSize));
        mTextView.setGravity(Gravity.CENTER);
        //// FIXME: 2017/6/13 by zz 文字居中问题
        mTextView.measure(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(mHigh, MeasureSpec.EXACTLY));
        addView(mTextView, 0);
    }
    
    private void initEmptyChildView() {
        mTextView = new ShineTextView(getContext(), null);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setDurationWithSeconds(shiningDuration);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapterUtil.getInstance().getScaledValue(textSize));
        mTextView.setGravity(Gravity.CENTER);
        //// FIXME: 2017/6/13 by zz 文字居中问题
        mTextView.measure(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(mHigh, MeasureSpec.EXACTLY));
        addView(mTextView, 0);
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = Math.abs(r - l);
        int high = Math.abs(b - t);
        if (mType == VERTICAL) {
            mTextView.layout(borderWidth, borderWidth, width - borderWidth, high - borderWidth);
        } else if (mType == HORIZONTAL) {
            mTextView.layout(borderWidth, borderWidth, width - borderWidth, high - borderWidth);
        } else {
            mTextView.layout(borderWidth, borderWidth, width - borderWidth, high - borderWidth);
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHigh);
    }
    
    public void setMachineName(String name) {
        mTextView.setText(name);
        mTextView.setGravity(Gravity.CENTER);
        //mTextView.setBackgroundColor(Color.WHITE);
    }
}
