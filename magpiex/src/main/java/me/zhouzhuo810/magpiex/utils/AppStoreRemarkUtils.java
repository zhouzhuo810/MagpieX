package me.zhouzhuo810.magpiex.utils;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/***
 * 跳转应用市场评分
 */
public class AppStoreRemarkUtils {
    
    /**
     * 跳转到app详情界面
     *
     * @param appPkg      App的包名
     * @param marketPkg   应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     * @param noStoreHint 无可用应用商店时的提示文字
     */
    public static void launchAppDetail(String appPkg, String marketPkg, String noStoreHint) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseUtil.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShortToast(noStoreHint == null ? "未检测到支持评论的应用商店~" : noStoreHint);
        }
    }
    
}
