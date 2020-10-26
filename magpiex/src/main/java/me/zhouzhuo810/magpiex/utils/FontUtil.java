package me.zhouzhuo810.magpiex.utils;

import android.graphics.Typeface;

/**
 * 字体工具
 * @author zhouzhuo810
 */
public class FontUtil {

    /**
     * 获取字体文件
     *
     * @param path assets中字体相对路径
     * @return 字体
     */
    public static Typeface getTypeFaceFromAssets(String path) {
        try {
            return Typeface.createFromAsset(BaseUtil.getApp().getAssets(), path);
        } catch (Exception e) {
            return null;
        }
    }
}
