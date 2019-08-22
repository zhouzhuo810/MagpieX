package me.zhouzhuo810.magpiex.utils;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * 基础工具类
 */
public class BaseUtil {
    
    private static Application mApp;
    
    private BaseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 在自定义的Application的onCreate方法中调用
     *
     * @param app Application
     */
    public static void init(Application app) {
        mApp = app;
        //顺便初始化屏幕适配工具类
        ScreenAdapterUtil.init(mApp);
    }
    
    public static Application getApp() {
        checkContextNull();
        return mApp;
    }
    
    private static void checkContextNull() {
        if (mApp == null) {
            throw new NullPointerException("Please invoke BaseUtil.init(Application app) method first");
        }
    }
    
    /**
     * 获取包名，版本号，版本名称等信息
     *
     * @param context 上下文
     * @return 信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new PackageInfo();
    }
    
    
    /**
     * Relaunch the application.
     */
    public static void relaunchApp() {
        PackageManager packageManager = getApp().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getApp().getPackageName());
        if (intent == null)
            return;
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        getApp().startActivity(mainIntent);
        System.exit(0);
    }
    
    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(getApp().getPackageName());
    }
    
    /**
     * Launch the application's details settings.
     *
     * @param packageName The name of the package.
     */
    public static void launchAppDetailsSettings(final String packageName) {
        if (isSpace(packageName))
            return;
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
