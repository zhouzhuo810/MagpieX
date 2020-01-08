package me.zhouzhuo810.magpiex.ui.widget.map.entity;

import android.graphics.Rect;

import me.zhouzhuo810.magpiex.ui.widget.map.view.MapTextView;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;


/**
 */
public class MapTextRectEntity extends BaseMapEntity {

    private String name;
    private int orientation;
    private int textSize;
    private int borderColor;
    private int borderWidthPx;
    private int shiningDuration;
    private int[] shiningColors;

    public MapTextRectEntity(String name, int borderColor, int borderWidthPx, Rect rim) {
        this.orientation = MapTextView.EMPTY;
        this.name = name;
        this.borderColor = borderColor;
        this.rim = rim;
        this.shiningDuration = 1;
        if (borderWidthPx <= 0) {
            this.borderWidthPx = ScreenAdapterUtil.getInstance().getScaledValue(2);
        } else {
            this.borderWidthPx = ScreenAdapterUtil.getInstance().getScaledValue(borderWidthPx);
        }
        this.textSize = 24;
    }

    public MapTextRectEntity(String name, int orientation, int borderColor, int borderWidthPx, int textSizePx, int shiningDurationSecond, int[] shiningColors, Rect rim) {
        this.name = name;
        this.borderColor = borderColor;
        if (textSizePx <= 0) {
            this.textSize = 24;
        } else {
            this.textSize = textSizePx;
        }
        if (borderWidthPx <= 0) {
            this.borderWidthPx = ScreenAdapterUtil.getInstance().getScaledValue(2);
        } else {
            this.borderWidthPx = ScreenAdapterUtil.getInstance().getScaledValue(borderWidthPx);
        }
        this.shiningDuration = shiningDurationSecond;
        this.orientation = orientation;
        this.shiningColors = shiningColors;
        this.rim = rim;
    }

    public int[] getShiningColors() {
        return shiningColors;
    }

    public void setShiningColors(int[] shiningColors) {
        this.shiningColors = shiningColors;
    }

    public int getShiningDuration() {
        return shiningDuration;
    }

    public void setShiningDuration(int shiningDuration) {
        this.shiningDuration = shiningDuration;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderWidthPx() {
        return borderWidthPx;
    }

    public void setBorderWidthPx(int borderWidthPx) {
        this.borderWidthPx = borderWidthPx;
    }
}
