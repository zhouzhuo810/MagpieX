package me.zhouzhuo810.magpiex.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Method;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class ViewUtil {
    private static final String METHOD_GET_MAX_WIDTH = "getMaxWidth";
    private static final String METHOD_GET_MAX_HEIGHT = "getMaxHeight";
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
        if (textView == null) {
            return;
        }
        String text = textView.getText().toString();
        if (use) {
            SpannableString sb = new SpannableString(text);
            UnderlineSpan underlineSpan = new UnderlineSpan();
            sb.setSpan(underlineSpan, 0, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(sb);
        } else {
            textView.setText(text);
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
    
    /**
     * 设置图标的颜色
     *
     * @param icon  ImageView
     * @param color 颜色值
     */
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
     * 设置FloatingActionButton的背景颜色
     *
     * @param color   颜色
     * @param buttons 按钮
     */
    public static void setFloatingActionButtonBgColor(@ColorInt int color, FloatingActionButton... buttons) {
        if (buttons != null) {
            for (FloatingActionButton btn : buttons) {
                if (btn != null) {
                    ColorStateList colorStateList = ColorStateList.valueOf(color);
                    btn.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    btn.setBackgroundTintList(colorStateList);
                }
            }
        }
    }
    
    /**
     * 设置TextView文字颜色
     *
     * @param textView TextView
     * @param resId    颜色资源id
     */
    public static void setTextColorRes(TextView textView, @ColorRes int resId) {
        textView.setTextColor(SimpleUtil.getColor(resId));
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
    
    public static void setTextViewMinWidth(TextView view, int value) {
        view.setMinWidth(value);
    }
    
    public static void setTextViewMinHeight(TextView view, int value) {
        view.setMinHeight(value);
    }
    
    public static int getMaxWidth(View view) {
        return getValue(view, METHOD_GET_MAX_WIDTH);
    }
    
    public static int getMaxHeight(View view) {
        return getValue(view, METHOD_GET_MAX_HEIGHT);
    }
    
    public static int getMinWidth(View view) {
        return view.getMinimumWidth();
    }

    public static int getTextViewMinWidth(TextView view) {
        return view.getMinWidth();
    }
    
    public static int getMinHeight(View view) {
        return view.getMinimumHeight();
    }

    public static int getTextViewMinHeight(TextView view) {
        return view.getMinHeight();
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
    
    /**
     * 移除 RecyclerView 的 item 动画
     * @param rv RecyclerView
     */
    public static void removeRecyclerViewItemAnimator(RecyclerView rv) {
        if (rv != null) {
            RecyclerView.ItemAnimator itemAnimator = rv.getItemAnimator();
            if (itemAnimator != null) {
                // 去除动画
                itemAnimator.setChangeDuration(0);
                itemAnimator.setAddDuration(0);
                itemAnimator.setMoveDuration(0);
                itemAnimator.setRemoveDuration(0);
            }
        }
    }
}
