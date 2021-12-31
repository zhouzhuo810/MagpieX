package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.DisplayUtil;

/**
 * 支持设置最大高度的ScrollView
 *
 * @author zhouzhuo810
 */
public class MaxHeightScrollView extends ScrollView {
    
    private int mMaxHeight;
    private float mPercentHeight;
    
    public MaxHeightScrollView(Context context) {
        super(context);
        initialize(context, null);
    }
    
    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }
    
    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }
    
    private void initialize(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
            float percentHeight = ta.getFloat(R.styleable.MaxHeightScrollView_mhs_max_height_percent, 0.7f);
            int maxHeight = ta.getDimensionPixelSize(R.styleable.MaxHeightScrollView_mhs_max_height_value, 0);
            if (maxHeight > 0) {
                mMaxHeight = maxHeight;
            } else {
                setPercentHeight(percentHeight);
            }
            ta.recycle();
        } else {
            setPercentHeight(0.7f);
        }
    }
    
    public void setPercentHeight(float percentHeight) {
        mPercentHeight = percentHeight;
        mMaxHeight = (int) (DisplayUtil.getScreenHeight() * mPercentHeight);
    }
    
    public void setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
        requestLayout();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
}
