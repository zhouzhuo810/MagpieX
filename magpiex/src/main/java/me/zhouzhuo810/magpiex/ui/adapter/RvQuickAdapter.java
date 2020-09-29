package me.zhouzhuo810.magpiex.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 解除BaseQuickAdapter的布局屏幕适配顾虑，继承这个不用考虑屏幕适配问题
 */
public abstract class RvQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public RvQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }
    
    public RvQuickAdapter(@Nullable List<T> data) {
        super(data);
    }
    
    public RvQuickAdapter(int layoutResId) {
        super(layoutResId);
    }
    
    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        K k = super.onCreateDefViewHolder(parent, viewType);
        int[] nestViewIds = getNestViewIds();
        if (nestViewIds != null && nestViewIds.length > 0) {
            k.setNestView(nestViewIds);
        }
        return k;
    }
    
    /**
     * Item中需要设置点击或长按事件的子View的Id
     */
    public abstract int[] getNestViewIds();
    
    @Override
    protected K createBaseViewHolder(View view) {
        K baseViewHolder = super.createBaseViewHolder(view);
        SimpleUtil.scaleView(baseViewHolder.itemView);
        return baseViewHolder;
    }
}
