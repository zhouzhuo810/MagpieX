package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.view.WindowManager;

public class DisplayUtil {
    
    private DisplayUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseUtil.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return BaseUtil.getApp().getResources().getDisplayMetrics().widthPixels;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return wm.getCurrentWindowMetrics().getBounds().width();
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }
    
    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) BaseUtil.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return BaseUtil.getApp().getResources().getDisplayMetrics().heightPixels;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return wm.getCurrentWindowMetrics().getBounds().height();
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }
    
    /**
     * convert px to its equivalent dp
     * <p>
     * 将px转换为与之相等的dp
     */
    public static int px2dp(float pxValue) {
        final float scale = BaseUtil.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    /**
     * convert dp to its equivalent px
     * <p>
     * 将dp转换为与之相等的px
     */
    public static int dp2px(float dipValue) {
        final float scale = BaseUtil.getApp().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    
    /**
     * convert px to its equivalent sp
     * <p>
     * 将px转换为sp
     */
    public static int px2sp(float pxValue) {
        final float fontScale = BaseUtil.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    
    
    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        final float fontScale = BaseUtil.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    
    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    public static float getScreenDensity() {
        return BaseUtil.getApp().getResources().getDisplayMetrics().density;
    }
    
    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    public static int getScreenDensityDpi() {
        return BaseUtil.getApp().getResources().getDisplayMetrics().densityDpi;
    }
    
    /**
     * 打开文档
     *
     * @param context   Context
     * @param dataType  {@link me.zhouzhuo810.magpiex.cons.MIME}
     * @param authority authority
     * @param path      文件路径
     */
    public static void actionView(Context context, String dataType, String authority, String path) {
        if (path == null) {
            return;
        }
        Uri uri = UriUtil.filePath2Uri(context, path, authority);
        actionView(context, dataType, uri);
    }
    
    /**
     * 打开文档
     *
     * @param context  Context
     * @param dataType {@link me.zhouzhuo810.magpiex.cons.MIME}
     * @param uri      文件Uri
     */
    public static void actionView(Context context, String dataType, Uri uri) {
        if (uri == null) {
            return;
        }
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.setDataAndType(uri, dataType);
        Intent chooser = Intent.createChooser(it, "请选择应用");
        if (chooser == null) {
            context.startActivity(it);
            return;
        }
        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
    
}
