package me.zhouzhuo810.magpiex.ui.widget.scroll;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 滚动管理类
 *
 * @author zhouzhuo810
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {
    
    private int mMillsPerPx = 20;
    
    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }
    
    public ScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
    
    public ScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    public void setMillsPerPixel(int millsPerPixel) {
        this.mMillsPerPx = millsPerPixel;
    }
    
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
            new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return ScrollLinearLayoutManager.this
                        .computeScrollVectorForPosition(targetPosition);
                }
                
                //This returns the milliseconds it takes to
                //scroll one pixel.
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return mMillsPerPx / displayMetrics.density;
                    //返回滑动一个pixel需要多少毫秒
                }
                
                
                @Override
                protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                    /*重写该函数使其滑动到底部时不减速*/
                }
                
                
                @Override
                protected int calculateTimeForScrolling(int dx) {
                    return super.calculateTimeForScrolling(dx);
                }
                
                @Override
                protected int calculateTimeForDeceleration(int dx) {
                    return 0;
                }
            };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
    
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
