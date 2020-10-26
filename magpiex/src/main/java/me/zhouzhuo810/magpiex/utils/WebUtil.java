package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

public class WebUtil {
    
    /**
     * 使用浏览器打开链接
     *
     * @param context Context
     * @param title   标题
     * @param url     链接
     * @return 是否成功
     */
    public static boolean openUrl(Context context, String title, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, title));
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Html 解码
     *
     * @param html 待解码的字符串
     * @return Html 解码后的字符串
     */
    @SuppressWarnings("deprecation")
    public static CharSequence fromHtml(final String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
    
}
