package me.zhouzhuo810.magpiex.ui.widget.map;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.BaseMapEntity;


/**
 * 自定义LayoutManager
 */
public class MapLayoutManager extends RecyclerView.LayoutManager {
    
    private List<BaseMapEntity> mFrames;
    
    public MapLayoutManager(List<BaseMapEntity> frames) {
        this.mFrames = frames;
    }
    
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        
        detachAndScrapAttachedViews(recycler);
        
        int size = Math.min(getItemCount(), mFrames.size());
        for (int i = 0; i < size; i++) {
            View view = recycler.getViewForPosition(i);
            //view.setBackgroundColor(Color.WHITE);
            Rect rect = mFrames.get(i).rim;
            addView(view);
            /*标明Item 的位置*/
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}

