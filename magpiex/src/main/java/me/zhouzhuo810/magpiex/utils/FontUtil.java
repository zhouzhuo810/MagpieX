package me.zhouzhuo810.magpiex.utils;

import android.graphics.Typeface;

import java.io.File;

/**
 * 字体工具
 *
 * @author zhouzhuo810
 */
public class FontUtil {
    
    /**
     * 加载 Assets 中的字体
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
    
    /**
     * 加载 File 中的字体
     *
     * @param filePath 字体路径
     * @return 字体
     */
    public static Typeface getTypeface(String filePath) {
        if (filePath == null) {
            return Typeface.DEFAULT;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return Typeface.createFromFile(filePath);
        }
        return Typeface.DEFAULT;
    }
    
}
