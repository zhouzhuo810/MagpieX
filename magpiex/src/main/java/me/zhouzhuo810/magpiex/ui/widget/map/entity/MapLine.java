package me.zhouzhuo810.magpiex.ui.widget.map.entity;

/**
 * Map的边框线,和SparseArray 一起用 由于不是水平就是垂直 所以保存一个开始和结束的位置
 */
public class MapLine {
    
    private int color;
    public float start, end;
    
    public MapLine(float start, float end, int color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }
    
    public boolean addLine(float s, float e) {
        if (s > end || e < start) {/*没有交集*/
            return false;
        } else if (s < start || e > end) {
            start = s < start ? s : start;
            end = e > end ? e : end;
            return true;
        } else {
            return false;
        }
    }
}
