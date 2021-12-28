package me.zhouzhuo810.magpiex.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.adapter.RvBaseAdapter;
import me.zhouzhuo810.magpiex.ui.widget.scroll.OnVerticalScrollListener;
import me.zhouzhuo810.magpiex.ui.widget.scroll.ScrollLinearLayoutManager;
import me.zhouzhuo810.magpiex.utils.ViewUtil;

/**
 * 滚动列表
 */
public class ScrollListRecyclerView<T> extends RecyclerView {
    
    protected boolean mIsScrolling;
    protected boolean mScrollEnable;
    private ScrollLinearLayoutManager mScrollLinearLayoutManager;
    private RvBaseAdapter<T> mAdapter;
    private List<T> mData;
    private boolean mIsTouched;
    private int mTopBottomDelay = 3000;
    
    public ScrollListRecyclerView(Context context) {
        super(context);
        init(context, null);
    }
    
    public ScrollListRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public ScrollListRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
    
    /**
     * 设置能否滚动
     *
     * @param enable 能否
     */
    public void setScrollEnable(boolean enable) {
        this.mScrollEnable = enable;
    }
    
    /**
     * 是否启动滚动
     *
     * @return 是否
     */
    public boolean isScrollEnable() {
        return mScrollEnable;
    }
    
    /**
     * 是否正在滚动
     *
     * @return 是否
     */
    public boolean isScrolling() {
        return mIsScrolling;
    }
    
    /**
     * 设置滚动速度
     *
     * @param millsPerPx 滚动1px需要多少毫秒
     */
    public void setScrollSpeed(int millsPerPx) {
        if (millsPerPx <= 0) {
            throw new RuntimeException("The speed value must > 0");
        }
        if (mScrollLinearLayoutManager != null) {
            mScrollLinearLayoutManager.setMillsPerPixel(millsPerPx);
        }
    }
    
    /**
     * 设置滚动到顶部和底部停留时间
     *
     * @param topBottomDelay 停留时间，毫秒
     */
    public void setTopBottomDelay(int topBottomDelay) {
        this.mTopBottomDelay = topBottomDelay;
    }
    
    private void init(Context context, AttributeSet attrs) {
        setLayoutManager(getScrollLayoutManager(context));
        ViewUtil.removeRecyclerViewItemAnimator(this);
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ScrollListRecyclerView);
            mScrollEnable = t.getBoolean(R.styleable.ScrollListRecyclerView_slr_scrollEnable, true);
            mTopBottomDelay = t.getInteger(R.styleable.ScrollListRecyclerView_slr_topBottomDelay, 3000);
            if (mTopBottomDelay < 0) {
                throw new RuntimeException("The 'slr_topBottomDelay' value must >= 0");
            }
            int mScrollSpeed = t.getInteger(R.styleable.ScrollListRecyclerView_slr_scrollSpeed, 20);
            setScrollSpeed(mScrollSpeed);
            t.recycle();
        } else {
            mScrollEnable = true;
            setScrollSpeed(20);
        }
        addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            protected void onScrolledToTop() {
                scrollTop();
            }
            
            @Override
            protected void onScrolledToBottom() {
                scrollBottom();
            }
            
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    mIsScrolling = false;
                } else if (newState == 2) {
                    mIsScrolling = true;
                }
            }
        });
    }
    
    /**
     * 刷新数据
     *
     * @param mData 数据
     */
    public void setNewData(List<T> mData) {
        if (mAdapter == null) {
            throw new RuntimeException("you must invoke ScrollListRecyclerView#setAdapter() method first.");
        }
        setNewData(mData, true);
    }
    
    public void setNewData(List<T> mData, boolean forceUpdate) {
        if (mAdapter == null) {
            throw new RuntimeException("you must invoke ScrollListRecyclerView#setAdapter() method first.");
        }
        setNewData(mData, forceUpdate, false);
    }
    
    private void setNewData(List<T> mData, boolean forceUpdate, boolean delay) {
        this.mData = mData;
        if (forceUpdate) {
            stopScroll();
            if (delay) {
                startScrollDelay(mTopBottomDelay);
            } else {
                startScrollImmediately();
            }
        } else {
            if (!mIsScrolling) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateAll(ScrollListRecyclerView.this.mData);
                    }
                });
            }
        }
    }
    
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof RvBaseAdapter) {
            mAdapter = (RvBaseAdapter<T>) adapter;
        } else {
            if (isInEditMode()) {
                return;
            }
            throw new RuntimeException("your adapter must extends RvBaseAdapter.");
        }
    }
    
    public ScrollLinearLayoutManager getScrollLayoutManager(Context context) {
        if (mScrollLinearLayoutManager == null) {
            mScrollLinearLayoutManager = new ScrollLinearLayoutManager(context);
        }
        return mScrollLinearLayoutManager;
    }
    
    public void scrollTop() {
        if (mScrollEnable && !mIsTouched) {
            try {
                setNewData(mData, true, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void scrollBottom() {
        if (mScrollEnable && !mIsTouched) {
            setNewData(mData, false);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        scrollToPosition(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, mTopBottomDelay);
        }
    }
    
    @Override
    public void stopScroll() {
        super.stopScroll();
        if (mAdapter != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter != null) {
                        mAdapter.updateAll(mData);
                    }
                }
            });
        }
    }
    
    public void startScrollDelay(int mills) {
        if (mScrollEnable) {
            if (!mIsScrolling) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mScrollEnable) {
                            try {
                                if (mData != null) {
                                    smoothScrollToPosition(mData.size());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, mills);
            }
        }
    }
    
    public void startScrollImmediately() {
        if (mScrollEnable) {
            try {
                if (mData != null) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (mData != null) {
                                smoothScrollToPosition(mData.size());
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouched = true;
                stopScroll();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsTouched = false;
                startScrollImmediately();
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }
    
    
}
