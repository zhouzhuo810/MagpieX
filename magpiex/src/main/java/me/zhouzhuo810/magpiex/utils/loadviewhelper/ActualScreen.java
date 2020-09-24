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
        int orientation = context.getResources().getConfiguration().orientation;
        return new float[]{orientation, displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, displayMetrics.densityDpi};
    }
    
}

