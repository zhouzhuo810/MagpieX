package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;


import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.DisplayUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;


public class MaxHeightRecyclerView extends RecyclerView {
    
    private int mMaxHeight;
    private float mPercentHeight;
    
    public MaxHeightRecyclerView(Context context) {
        super(context);
    }
    
    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }
    
    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }
    
    private void initialize(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
        float percentHeight = ta.getFloat(R.styleable.MaxHeightRecyclerView_mhr_max_height_percent, 0.7f);
        int maxHeight = ta.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_mhr_max_height_value, 0);
        if (maxHeight > 0) {
            mMaxHeight = maxHeight;
        } else {
            setPercentHeight(percentHeight);
        }
        ta.recycle();
        
        if (!isInEditMode()) {
            mMaxHeight = SimpleUtil.getScaledValue(mMaxHeight);
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
