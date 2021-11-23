package me.zhouzhuo810.magpiex.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class PackageUtil {
    
    /**
     * 检查手机上是否安装了指定的软件，注意在AndroidManifest.xml中添加<queries>标签
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 是否未安装 true：未安装，false：已安装
     */
    public static boolean isAppNotInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return !packageNames.contains(packageName);
    }
    
    /**
     * 获取APP版本号
     *
     * @return 版本号
     */
    public static long getVersionCode() {
        return getVersionCode(1);
    }
    
    /**
     * 获取APP版本号
     *
     * @param defaultVersionCode 默认版本号
     * @return 版本号
     */
    public static long getVersionCode(long defaultVersionCode) {
        long versionCode = defaultVersionCode;
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                versionCode = BaseUtil.getApp().getPackageManager()
                    .getPackageInfo(BaseUtil.getApp().getPackageName(), 0).getLongVersionCode();
            } else {
                versionCode = BaseUtil.getApp().getPackageManager()
                    .getPackageInfo(BaseUtil.getApp().getPackageName(), 0).versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
        
    }
    
    /**
     * 获取APP版本名称
     *
     * @return 版本名称
     */
    public static String getVersionName() {
        return getVersionName(null);
    }
    
    /**
     * 获取APP版本名称
     *
     * @param defaultVersion 默认版本名称
     * @return 版本名称
     */
    public static String getVersionName(String defaultVersion) {
        String verName = defaultVersion == null ? "1.0.0" : defaultVersion;
        try {
            verName = BaseUtil.getApp().getPackageManager().
                getPackageInfo(BaseUtil.getApp().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }
    
    /**
     * 打开某个应用，注意在AndroidManifest.xml中添加<queries>标签
     *
     * @param context Context
     * @param packageName 应用包名
     * @throws PackageManager.NameNotFoundException
     */
    public static void openApp(Context context, String packageName) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(packageName, 0);
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String name = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(name, className);
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
