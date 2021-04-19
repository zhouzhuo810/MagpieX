package me.zhouzhuo810.magpiex.ui.widget.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapLine;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapTextRectEntity;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;


/**
 * Created by Administrator on 2017/6/19.
 */

public class MapItemDecoration extends RecyclerView.ItemDecoration {

    private List<MapTextRectEntity> mEntity;
    private Paint mPaint;

    // FIXME: 2017/6/19 自定义  优化算法 By lirenhao
    private SparseArray<List<MapLine>> vertical, horizontal;
    private float[] lines;

    public MapItemDecoration(List<MapTextRectEntity> entity) {
        this.mEntity = entity;
        mPaint = new Paint();
        mPaint.setStrokeWidth(SimpleUtil.getScaledValue(2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
    }

    public void setMapTextRectEntity(List<MapTextRectEntity> entity) {
        this.mEntity = entity;
    }

    /**
     * 计算边框,初始化时调用
     */
    private void calculationDecoration() {
        if (mEntity == null)
            throw new IllegalAccessError("Machine Entity is Empty");
        vertical = new SparseArray<>();
        horizontal = new SparseArray<>();

        for (MapTextRectEntity entity : mEntity) {
            Rect rect = entity.getRim();
            /*计算四条直线*/
            int color = entity.getBorderColor();
            calculationVertical(rect.left, rect.top, rect.bottom, color);
            calculationVertical(rect.right, rect.top, rect.bottom, color);
            calculationHorizontal(rect.top, rect.left, rect.right, color);
            calculationHorizontal(rect.bottom, rect.left, rect.right, color);
        }

        Log.i("MapItemDecoration", "vertical size " + vertical.size());
        Log.i("MapItemDecoration", "horizontal size " + horizontal.size());

        /*转化成float*/
        List<Float> tempMapLines = new ArrayList<>();
        /*竖直方向上*/
        for (int i = 0; i < vertical.size(); i++) {
            int v = vertical.keyAt(i);
            List<MapLine> lines = vertical.valueAt(i);
            for (MapLine line : lines) {
                tempMapLines.add((float) v);
                tempMapLines.add(line.start);
                tempMapLines.add((float) v);
                tempMapLines.add(line.end);
            }
        }
        /*水平方向上*/
        for (int i = 0; i < horizontal.size(); i++) {
            int h = horizontal.keyAt(i);
            List<MapLine> lines = horizontal.valueAt(i);
            for (MapLine line : lines) {
                tempMapLines.add(line.start);
                tempMapLines.add((float) h);
                tempMapLines.add(line.end);
                tempMapLines.add((float) h);
            }
        }
        /*转数组*/
        lines = new float[tempMapLines.size()];
        for (int i = 0; i < tempMapLines.size(); i++) {
            lines[i] = tempMapLines.get(i);
        }
        vertical.clear();
        horizontal.clear();
        //Log.i("MapItemDecoration","line size "+lines.length);
    }

    private void calculationVertical(int v, int start, int end, int color) {
        List<MapLine> vls = vertical.get(v);
        if (vls == null) {
            /*直接添加*/
            vls = new ArrayList<>();
            vls.add(new MapLine(start, end, color));
            vertical.append(v, vls);
        }

        boolean ifAdd = false;
        for (int i = 0; i < vls.size(); i++) {
            if (vls.get(i).addLine(start, end)) {
                ifAdd = true;
            }
        }
        if (!ifAdd) {
            /*不存在  直接添加*/
            vls.add(new MapLine(start, end, color));
        }
    }

    private void calculationHorizontal(int h, int start, int end, int color) {
        List<MapLine> hls = horizontal.get(h);
        if (hls == null) {
            /*直接添加*/
            hls = new ArrayList<>();
            hls.add(new MapLine(start, end, color));
            horizontal.append(h, hls);
        }

        boolean ifAdd = false;
        for (int i = 0; i < hls.size(); i++) {
            if (hls.get(i).addLine(start, end)) {
                ifAdd = true;
            }
        }
        if (!ifAdd) {
            /*不存在  直接添加*/
            hls.add(new MapLine(start, end, color));
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        /*初始化边框线*/
        for (MapTextRectEntity entity : mEntity) {
            Rect rect = entity.getRim();
            /*计算四条直线*/
            mPaint.setStrokeWidth(entity.getBorderWidthPx());
            mPaint.setColor(entity.getBorderColor());
            c.drawRect(rect, mPaint);
        }
    }
}
