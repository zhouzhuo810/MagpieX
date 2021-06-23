package me.zhouzhuo810.magpiex.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 解除 BaseMultiItemQuickAdapter 的布局屏幕适配顾虑，继承这个不用考虑屏幕适配问题
 *
 * @author zhouzhuo810
 */
public abstract class RvMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {
    
    public RvMultiItemQuickAdapter() {
        super();
        initClickIdsIfNeeded();
    }
    
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RvMultiItemQuickAdapter(List<T> data) {
        super(data);
        initClickIdsIfNeeded();
    }
    
    
    private void initClickIdsIfNeeded() {
        int[] nestViewIds = getNestViewIds();
        if (nestViewIds != null && nestViewIds.length > 0) {
            addChildClickViewIds(nestViewIds);
        }
    }
    
    @NotNull
    @Override
    protected K createBaseViewHolder(@NonNull View view) {
        K baseViewHolder = super.createBaseViewHolder(view);
        if (!disableScale()) {
            SimpleUtil.scaleView(baseViewHolder.itemView);
        }
        return baseViewHolder;
    }
    
    /**
     * 是否禁用缩放
     *
     * @return 是否，默认false
     */
    protected boolean disableScale() {
        return false;
    }
    
    /**
     * Item中需要设置点击或长按事件的子View的Id
     *
     * @return ids
     */
    public abstract int[] getNestViewIds();
    
}
