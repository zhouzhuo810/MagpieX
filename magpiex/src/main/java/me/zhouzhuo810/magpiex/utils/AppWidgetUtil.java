package me.zhouzhuo810.magpiex.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

/**
 * 桌面插件刷新工具类
 *
 * @author zhouzhuo810
 * @date 2021/12/21 1:36 下午
 */
public class AppWidgetUtil {
    
    /**
     * 通过发送广播的形式刷新桌面插件
     */
    public static void justUpdateAll() {
        try {
            BaseUtil.getApp().sendBroadcast(
                new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                    .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{1})
            );
        } catch (Exception ignored) {
        }
    }
    
    
    /**
     * 刷新特定类型桌面插件
     *
     * @param cls 继承 {@link android.appwidget.AppWidgetProvider} 的类
     */
    public static void justUpdate(@NonNull Class<?> cls, @NonNull RemoteViews remoteViews) {
        try {
            ComponentName componentName = new ComponentName(BaseUtil.getApp(), cls);
            AppWidgetManager.getInstance(BaseUtil.getApp()).updateAppWidget(componentName, remoteViews);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
