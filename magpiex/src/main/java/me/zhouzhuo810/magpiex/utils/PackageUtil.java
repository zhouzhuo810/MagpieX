package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class PackageUtil {
    
    /**
     * 检查手机上是否安装了指定的软件
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
    
}
