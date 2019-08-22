package me.zhouzhuo810.magpiex.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import android.view.View;

import java.text.DecimalFormat;

/**
 * 常用方法简化
 */
public class SimpleUtil {
    
    /**
     * 缩放View
     *
     * @param v 要缩放的View
     */
    public static void scaleView(View v) {
        ScreenAdapterUtil.getInstance().loadView(v);
    }
    
    /**
     * 重置缩放
     */
    public static void resetScale(Context context) {
        ScreenAdapterUtil.getInstance().reset(context);
    }
    
    /**
     * 缩放View
     *
     * @param v 要缩放的View
     */
    public static void scaleViewByWidthHeight(View v) {
        ScreenAdapterUtil.getInstance().loadView(v, true);
    }
    
    /**
     * 缩放值按宽度（默认）
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static int getScaledValue(int px) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px);
    }
    
    /**
     * 缩放值
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static int getScaledValueByHeight(int px) {
        return ScreenAdapterUtil.getInstance().getScaledValueByHeight(px);
    }
    
    /**
     * 获取字符串资源
     *
     * @param resId 资源ID
     * @return 字符串
     */
    public static String getString(@StringRes int resId) {
        return BaseUtil.getApp().getString(resId);
    }
    
    /**
     * 获取颜色资源
     *
     * @param resId 资源ID
     * @return 颜色值
     */
    public static int getColor(@ColorRes int resId) {
        return BaseUtil.getApp().getResources().getColor(resId);
    }
    
    /**
     * 获取字符串数组资源
     *
     * @param resId 资源ID
     * @return 字符串数组
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return BaseUtil.getApp().getResources().getStringArray(resId);
    }
    
    /**
     * 获取assets文件夹中的字体
     *
     * @param path 路径，如fonts/xxx.ttf
     * @return Typeface对象，没有则返回null
     */
    public static Typeface getFontFromAssets(String path) {
        return FontUtil.getTypeFaceFromAssets(path);
    }
    
    /**
     * 获取assets文件夹中的文件的字符串内容
     *
     * @param path 路径，如json/test.json
     * @return 文件内容字符串
     */
    public static String getFileContentFromAssets(String path) {
        return AssetsUtil.getFileToStringFromAssets(path);
    }
    
    /**
     * 格式化浮点数为字符串
     *
     * @param f 浮点数
     * @param n 保留小数位
     * @return 字符串
     */
    public static String formatFloatWithDecimal(float f, int n) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#0.");
        if (n <= 1) {
            pattern.append("0");
        } else {
            for (int i = 0; i < n; i++) {
                pattern.append("0");
            }
        }
        return new DecimalFormat(pattern.toString()).format(f);
    }
    
    /**
     * 沉浸式状态栏
     * 在setContentView()之后调用
     *
     * @param activity Activity
     * @param darkFont 状态栏字体是否黑色
     */
    public static void transparentStatusBar(Activity activity, boolean darkFont) {
        if (activity == null) {
            return;
        }
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(activity, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(activity);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(activity, darkFont)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(activity, 0x55000000);
        }
    }
}
