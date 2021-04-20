package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.map.MapAdapter;
import me.zhouzhuo810.magpiex.ui.widget.map.MapItemDecoration;
import me.zhouzhuo810.magpiex.ui.widget.map.MapLayoutManager;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.BaseMapEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapTextRectEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.utils.RectUtil;


/**
 * 画格子和文字,且支持背景色闪烁、动态文字大小，动态格子线条颜色的RecyclerFView
 * @author zhouzhuo810
 */
public class MapRecyclerView extends RecyclerView {
    
    private MapEntity mMapEntity;
    private MapLayoutManager mManager;
    private MapAdapter mMapAdapter;
    
    /**
     * 边框
     */
    private MapItemDecoration mMapItemDecoration;
    
    private boolean isRvLayout = false;
    private int rvWidth;
    private int rvHigh;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    
    public MapRecyclerView(Context context) {
        super(context);
    }
    
    public MapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    /**
     * 初始化
     */
    private void init() {
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isRvLayout) {
                    return;
                }
                isRvLayout = true;
                rvWidth = getWidth();
                rvHigh = getHeight();
                restMap();
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rvWidth = w;
        rvHigh = h;
        restMap();
    }
    
    public void setMapEntity(MapEntity mapEntity) {
        this.mMapEntity = mapEntity;
        restMap();
    }
    
    
    /**
     * 获取宽度
     */
    public int getRvWidth() {
        return rvWidth;
    }
    
    /**
     * 获取高度
     */
    public int getRvHigh() {
        return rvHigh;
    }
    
    
    private void restMap() {
        if (!isRvLayout || mMapEntity == null) {
            return;
        }
        
        /*
         * 1.计算出实际尺寸
         * 2.layoutManager adapter
         */
        
        float zoomSize = (rvWidth * 1.0f) / mMapEntity.mapWidth;
        
        List<MapTextRectEntity> realMachine = new ArrayList<>();
        for (int i = 0; i < mMapEntity.getMapData().size(); i++) {
            MapTextRectEntity entity = mMapEntity.getMapData().get(i);
            realMachine.add(new MapTextRectEntity(entity.getName(), entity.getOrientation(), entity.getBorderColor(),
                entity.getBorderWidthPx(), entity.getTextSize(), entity.getShiningDuration(), entity.getShiningColors(),
                RectUtil.scale(entity.rim, zoomSize)));
        }
        // TODO: 2017/6/10 任意Item种类 By renhao
        List<BaseMapEntity> frames = new ArrayList<BaseMapEntity>(realMachine);
        
        mManager = new MapLayoutManager(frames);
        mMapAdapter = new MapAdapter(this.getContext(), realMachine);
        
        setLayoutManager(mManager);
        setAdapter(mMapAdapter);
        
        /*添加边框*/
        if (mMapItemDecoration == null) {
            mMapItemDecoration = new MapItemDecoration(realMachine);
            addItemDecoration(mMapItemDecoration);
        } else {
            mMapItemDecoration.setMapTextRectEntity(realMachine);
        }
        
        mMapEntity.clear();
    }
    
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mOnGlobalLayoutListener != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            mOnGlobalLayoutListener = null;
        }
        init();
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mOnGlobalLayoutListener != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            mOnGlobalLayoutListener = null;
        }
    }
}
