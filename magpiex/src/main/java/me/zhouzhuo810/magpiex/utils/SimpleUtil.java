package me.zhouzhuo810.magpiex.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import me.zhouzhuo810.magpiex.R;

/**
 * 常用方法简化
 *
 * @author zhouzhuo810
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
     * 缩放值自动（默认）- int
     *
     * @param px 原来值
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static int getScaledValue(int px) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, false);
    }
    
    /**
     * 缩放值自动（默认）- int
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static int getScaledValue(int px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, isFontSize);
    }
    
    
    /**
     * 缩放值自动（默认）- float
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static float getScaledValue(float px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, isFontSize);
    }
    
    /**
     * 缩放值自动（默认）- float
     *
     * @param px 原来值
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static float getScaledValue(float px) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, false);
    }
    
    /**
     * 缩放值自动（默认）- int
     *
     * @param px         原来值
     * @param horizontal 是否水平方向
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static int getScaledValue(int px, boolean horizontal, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, horizontal, isFontSize);
    }
    
    
    /**
     * 缩放值自动（默认）- float
     *
     * @param px         原来值
     * @param horizontal 是否水平方向
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值，根据screen_adapt_type决定按宽度还是高度
     */
    public static float getScaledValue(float px, boolean horizontal, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValue(px, horizontal, isFontSize);
    }
    
    /**
     * 缩放值 - int
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static int getScaledValueByWidth(int px) {
        return ScreenAdapterUtil.getInstance().getScaledValueByWidth(px, false);
    }
    
    /**
     * 缩放值 - int
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值
     */
    public static int getScaledValueByWidth(int px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValueByWidth(px, isFontSize);
    }
    
    /**
     * 缩放值 - float
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值
     */
    public static float getScaledValueByWidth(float px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValueByWidth(px, isFontSize);
    }
    
    
    /**
     * 缩放值 - float
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static float getScaledValueByWidth(float px) {
        return ScreenAdapterUtil.getInstance().getScaledValueByWidth(px, false);
    }
    
    /**
     * 缩放值按高度 - int
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static int getScaledValueByHeight(int px) {
        return ScreenAdapterUtil.getInstance().getScaledValueByHeight(px, false);
    }
    
    /**
     * 缩放值按高度 - int
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值
     */
    public static int getScaledValueByHeight(int px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValueByHeight(px, isFontSize);
    }
    
    /**
     * 缩放值按高度 - float
     *
     * @param px 原来值
     * @return 缩放后的值
     */
    public static float getScaledValueByHeight(float px) {
        return ScreenAdapterUtil.getInstance().getScaledValueByHeight(px, false);
    }
    
    /**
     * 缩放值按高度 - float
     *
     * @param px         原来值
     * @param isFontSize 是否是字体，如果是，会x配置里的字体缩放倍数
     * @return 缩放后的值
     */
    public static float getScaledValueByHeight(float px, boolean isFontSize) {
        return ScreenAdapterUtil.getInstance().getScaledValueByHeight(px, isFontSize);
    }
    
    /**
     * 设置文字字体大小
     *
     * @param tv         TextView
     * @param textSizePx 设计图的px值。
     */
    public static void setTextSizeInPx(TextView tv, float textSizePx) {
        if (tv != null) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getScaledValue(textSizePx, true));
        }
    }
    
    /**
     * 设置缩放Tag，如果true，则说明此View已经被缩放过了，则不会再次缩放。
     *
     * @param view     View
     * @param hasScale 是否已经缩放
     */
    public static void setScaleTag(View view, boolean hasScale) {
        if (view == null) {
            return;
        }
        view.setTag(R.id.view_scale_tag, hasScale);
    }
    
    /**
     * 判断View是否被缩放过
     *
     * @param view View
     */
    public static boolean hasScaled(View view) {
        if (view == null) {
            return false;
        }
        Object tag = view.getTag(R.id.view_scale_tag);
        return tag instanceof Boolean && (Boolean) tag;
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
        return ResourcesCompat.getColor(BaseUtil.getApp().getResources(), resId, BaseUtil.getApp().getTheme());
    }
    
    public static Drawable getDrawable(@DrawableRes int resId) {
        return ResourcesCompat.getDrawable(BaseUtil.getApp().getResources(), resId, BaseUtil.getApp().getTheme());
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
        return NumberUtil.formatFloatWithDecimal(f, n);
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
