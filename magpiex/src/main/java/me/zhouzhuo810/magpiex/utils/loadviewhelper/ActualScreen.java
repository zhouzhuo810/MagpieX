package me.zhouzhuo810.magpiex.utils.loadviewhelper;


import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public class ActualScreen {
    
    public static float[] screenInfo(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        if (windowManager == null) {
            return null;
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);
        int orientation = context.getResources().getConfiguration().orientation;
        int rotation = defaultDisplay.getRotation();
        // FIXME by 周卓 时间：2020/9/25 10:37 AM 由于获取orientation有延迟，而rotation没有延迟，为什么不直接使用rotation判断，是因为横屏设备和竖屏设备的rotation是反着的。
        if (orientation == Configuration.ORIENTATION_PORTRAIT && (rotation == 1 || rotation == 3)) {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE && (rotation == 0 || rotation == 4)) {
            orientation = Configuration.ORIENTATION_PORTRAIT;
        }
        return new float[]{orientation, displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, displayMetrics.densityDpi};
    }
    
}

