package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import androidx.annotation.DrawableRes;

/**
 * Glide 加载图片工具
 *
 * @author zhouzhuo810
 * @date 2021/11/23 9:38 上午
 */
public class GlideUtil {
    
    /**
     * 加载图片
     *
     * @param context Context
     * @param url     图片url
     * @param iv      目标ImageView
     */
    public static void load(Context context, String url, ImageView iv) {
        load(context, url, 0, iv);
    }
    
    /**
     * 加载图片
     *
     * @param context Context
     * @param url     图片url
     * @param radius  圆角
     * @param iv      目标ImageView
     */
    public static void load(Context context, String url, int radius, ImageView iv) {
        load(context, url, -1, -1, 0, 0, radius, iv);
    }
    
    /**
     * 加载图片
     *
     * @param context     Context
     * @param url         图片路径
     * @param placeholder 占位图
     * @param error       错误图
     * @param iv          目标ImageView
     */
    public static void load(Context context, String url, @DrawableRes int placeholder, @DrawableRes int error, ImageView iv) {
        load(context, url, -1, -1, placeholder, error, 0, iv);
    }
    
    /**
     * 加载图片
     *
     * @param context     Context
     * @param url         图片路径
     * @param placeholder 占位图
     * @param error       错误图
     * @param radius      圆角
     * @param iv          目标ImageView
     */
    public static void load(Context context, String url, @DrawableRes int placeholder, @DrawableRes int error, int radius, ImageView iv) {
        load(context, url, -1, -1, placeholder, error, radius, iv);
    }
    
    /**
     * 加载图片
     *
     * @param context     Context
     * @param url         图片路径
     * @param width       宽度
     * @param height      高度
     * @param placeholder 占位图
     * @param error       错误图
     * @param radius      圆角
     * @param iv          目标ImageView
     */
    public static void load(Context context, String url, int width, int height, @DrawableRes int placeholder, @DrawableRes int error, int radius, ImageView iv) {
        if (ActivityUtil.isDestroy(context) || iv == null) {
            return;
        }
        RequestBuilder<Drawable> rb = Glide.with(context)
            .load(url)
            .override(width, height)
            .placeholder(placeholder)
            .error(error);
        if (radius > 0) {
            rb = rb.transform(new RoundedCorners(radius));
        }
        rb.into(iv);
    }
}
