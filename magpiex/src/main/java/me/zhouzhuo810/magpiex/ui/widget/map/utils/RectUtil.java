package me.zhouzhuo810.magpiex.ui.widget.map.utils;

import android.graphics.Rect;

/**
 * @author AnotherMe17
 */
public class RectUtil {

    public static Rect scale(Rect rect, float scale) {
        return new Rect((int) (rect.left * scale),
                (int) (rect.top * scale),
                (int) (rect.right * scale),
                (int) (rect.bottom * scale));
    }
}
