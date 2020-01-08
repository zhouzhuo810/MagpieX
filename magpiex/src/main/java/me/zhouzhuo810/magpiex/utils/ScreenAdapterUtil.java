package me.zhouzhuo810.magpiex.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import me.zhouzhuo810.magpiex.utils.loadviewhelper.AbsLoadViewHelper;
import me.zhouzhuo810.magpiex.utils.loadviewhelper.LoadViewHelper;

/**
 * 屏幕适配工具类
 */
public class ScreenAdapterUtil {
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
            public AbsLoadViewHelper provide(Context context, boolean scaleWidthAndHeight, int designWidth, int designHeight, int designDpi, float fontSize, String unit) {
                return new LoadViewHelper(context, scaleWidthAndHeight, designWidth, designHeight, designDpi, fontSize, unit);
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
            throw new RuntimeException("Please add 'design_width', 'design_dpi', 'font_size' and 'unit' meta-data tag in your application.");
        }
        int designWidth = applicationInfo.metaData.getInt("design_width");
        int designHeight = applicationInfo.metaData.getInt("design_height");
        boolean scaleWidthAndHeight = (designHeight > 0);
        int designDpi = applicationInfo.metaData.getInt("design_dpi");
        float fontSize = applicationInfo.metaData.getFloat("font_size");
        String unit = applicationInfo.metaData.getString("unit");
        sLoadViewHelper = provider.provide(context, scaleWidthAndHeight, designWidth, designHeight, designDpi, fontSize, unit);
    }
    
    public interface IProvider {
        AbsLoadViewHelper provide(Context context, boolean scaleWidthAndHeight, int designWidth, int designheight,
                                  int designDpi, float fontSize, String unit);
    }
}
