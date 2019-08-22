package me.zhouzhuo810.magpiex.utils;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.ColorRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;

public class ViewUtil {
    private static final String METHOD_GET_MAX_WIDTH = "getMaxWidth";
    private static final String METHOD_GET_MAX_HEIGHT = "getMaxHeight";
    private static final String METHOD_GET_MIN_WIDTH = "getMinWidth";
    private static final String METHOD_GET_MIN_HEIGHT = "getMinHeight";
    private static final String METHOD_SET_MAX_WIDTH = "setMaxWidth";
    private static final String METHOD_SET_MAX_HEIGHT = "setMaxHeight";

    private ViewUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * TextView设置下划线
     *
     * @param textView TextView
     * @param use      true:使用下划线;false:清除下划线;
     */
    public static void setTextUnderline(TextView textView, boolean use) {
        if (use) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
    }

    /**
     * TextView设置删除线
     *
     * @param textView TextView
     * @param use      true:使用删除线;false:清除删除线;
     */
    public static void setTextDeleteLine(TextView textView, boolean use) {
        if (use) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
    
    //设置图标的颜色
    public static void setIconColor(ImageView icon, int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int a = Color.alpha(color);
        float[] colorMatrix = new float[]{0, 0, 0, 0, r, 0, 0, 0, 0, g, 0, 0, 0, 0, b, 0, 0, 0, (float) a / 255, 0};
        try {
            icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置TextView文字颜色
     *
     * @param textView TextView
     * @param resId    颜色资源id
     */
    public static void setTextColorRes(TextView textView, @ColorRes int resId) {
        textView.setTextColor(BaseUtil.getApp().getResources().getColor(resId));
    }

    public static void setMaxWidth(View view, int value) {
        setValue(view, METHOD_SET_MAX_WIDTH, value);
    }

    public static void setMaxHeight(View view, int value) {
        setValue(view, METHOD_SET_MAX_HEIGHT, value);
    }

    public static void setMinWidth(View view, int value) {
        view.setMinimumWidth(value);
    }

    public static void setMinHeight(View view, int value) {
        view.setMinimumHeight(value);
    }

    public static int getMaxWidth(View view) {
        return getValue(view, METHOD_GET_MAX_WIDTH);
    }

    public static int getMaxHeight(View view) {
        return getValue(view, METHOD_GET_MAX_HEIGHT);
    }

    public static int getMinWidth(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumWidth();
        } else {
            return getValue(view, METHOD_GET_MIN_WIDTH);
        }
    }

    public static int getMinHeight(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumHeight();
        } else {
            return getValue(view, METHOD_GET_MIN_HEIGHT);
        }
    }

    private static int getValue(View view, String getterMethodName) {
        int result = 0;
        try {
            Method getValueMethod = view.getClass().getMethod(getterMethodName);
            result = (int) getValueMethod.invoke(view);
        } catch (Exception e) {
            // do nothing
        }
        return result;
    }

    private static void setValue(View view, String setterMethodName, int value) {
        try {
            Method setValueMethod = view.getClass().getMethod(setterMethodName);
            setValueMethod.invoke(view, value);
        } catch (Exception e) {
            // do nothing
        }
    }
}
