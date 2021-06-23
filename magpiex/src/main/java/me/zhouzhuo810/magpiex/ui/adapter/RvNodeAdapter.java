package me.zhouzhuo810.magpiex.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.annotation.NonNull;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 解除 BaseNodeAdapter 的布局屏幕适配顾虑，继承这个不用考虑屏幕适配问题
 *
 * @author zhouzhuo810
 */
public abstract class RvNodeAdapter extends BaseNodeAdapter {
    
    public RvNodeAdapter() {
        super();
        initClickIdsIfNeeded();
    }
    
    public RvNodeAdapter(@Nullable List<BaseNode> nodeList) {
        super(nodeList);
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
    protected BaseViewHolder createBaseViewHolder(@NonNull View view) {
        BaseViewHolder baseViewHolder = super.createBaseViewHolder(view);
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
