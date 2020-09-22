package me.zhouzhuo810.magpiex.utils.loadviewhelper;


import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

import static android.content.Context.WINDOW_SERVICE;

public class ActualScreen {
    
    public static float[] screenInfo(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        if (windowManager == null) {
            return null;
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        return new float[]{(rotation == 1 || rotation == 3) ? Configuration.ORIENTATION_LANDSCAPE : Configuration.ORIENTATION_PORTRAIT,
            displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, displayMetrics.densityDpi};
    }
    
}

