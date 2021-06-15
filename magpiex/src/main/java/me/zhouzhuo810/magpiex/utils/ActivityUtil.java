package me.zhouzhuo810.magpiex.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

/**
 * ActivityUtil
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:48 PM
 */
public class ActivityUtil {
    
    public static Bundle getOptionsBundle(final Context context,
                                          final int enterAnim,
                                          final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }
    
    
    public static Bundle getOptionsBundle(final Activity activity,
                                          final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        if (sharedElements == null) {
            return null;
        }
        int len = sharedElements.length;
        if (len <= 0) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }
    
    
    /**
     * 判断Activity是否销毁，常用于Glide加载图片或清理图片前判断；
     *
     * @param context Context
     * @return 是否
     */
    public static boolean isDestroy(Context context) {
        if (context == null) {
            return true;
        }
        if (context instanceof Activity) {
            Activity act = (Activity) context;
            return act == null || act.isFinishing() ||
                (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed());
        }
        return false;
    }
}
