package me.zhouzhuo810.magpiex.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 解除BaseMultiItemQuickAdapter的布局屏幕适配顾虑，继承这个不用考虑屏幕适配问题
 */
public abstract class RvMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RvMultiItemQuickAdapter(List<T> data) {
        super(data);
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
