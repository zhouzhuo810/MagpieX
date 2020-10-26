package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WebUtil {
    
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
    
    
}
