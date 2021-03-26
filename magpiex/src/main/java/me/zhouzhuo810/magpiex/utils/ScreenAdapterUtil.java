package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import me.zhouzhuo810.magpiex.utils.loadviewhelper.AbsLoadViewHelper;
import me.zhouzhuo810.magpiex.utils.loadviewhelper.LoadViewHelper;

/**
 * 屏幕适配工具类
 */
public class ScreenAdapterUtil {
    /**
     * 自动，横屏设备按高度，竖屏设备按宽度(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920)
     */
    public static final String SCREEN_ADAPT_TYPE_AUTO = "auto";
    /**
     * 按宽度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920)
     */
    public static final String SCREEN_ADAPT_TYPE_WIDTH = "width";
    /**
     * 按高度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920)
     */
    public static final String SCREEN_ADAPT_TYPE_HEIGHT = "height";
    /**
     * 宽按宽度缩放，高按高度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920)
     */
    public static final String SCREEN_ADAPT_TYPE_WH = "wh";
    
    private ScreenAdapterUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    private static AbsLoadViewHelper sLoadViewHelper;
    
    public static AbsLoadViewHelper getInstance() {
        return sLoadViewHelper;
    }
    
    /**
     * 在Application的onCreate中调用此方法
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        init(context, new ScreenAdapterUtil.IProvider() {
            @Override
            public AbsLoadViewHelper provide(Context context, String adaptType, int designWidth, int designHeight, int designDpi, float fontSize, String unit) {
                return new LoadViewHelper(context, adaptType, designWidth, designHeight, designDpi, fontSize, unit);
            }
        });
    }
    
    public static void init(Context context, ScreenAdapterUtil.IProvider provider) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context
                .getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            throw new RuntimeException("Please add 'design_width', 'design_height', 'design_dpi', 'font_size' and 'unit' meta-data tag in your application.");
        }
        int designWidth = applicationInfo.metaData.getInt("design_width");
        int designHeight = applicationInfo.metaData.getInt("design_height");
        if (designWidth <= 0 || designHeight <= 0) {
            throw new RuntimeException("'design_width' and 'design_height' must > 0");
        }
        String adaptType = applicationInfo.metaData.getString("screen_adapt_type");
        if (adaptType == null) {
            adaptType = SCREEN_ADAPT_TYPE_AUTO;
        }
        int designDpi = applicationInfo.metaData.getInt("design_dpi");
        float fontSize = applicationInfo.metaData.getFloat("font_size");
        String unit = applicationInfo.metaData.getString("unit");
        sLoadViewHelper = provider.provide(context, adaptType, designWidth, designHeight, designDpi, fontSize, unit);
    }
    
    public interface IProvider {
        AbsLoadViewHelper provide(Context context, String adaptType, int designWidth, int designheight,
                                  int designDpi, float fontSize, String unit);
    }
}
