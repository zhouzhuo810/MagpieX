package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;

public class ColorUtil {


    /**
     * 设置部分字符串的颜色并填充到TextView，适用于模糊搜索。
     *
     * @param textView        tv
     * @param selectionString selectionString
     * @param fullString      fullString
     * @param selectionColor  selectionColor
     */
    public static void setSearchContentTextColor(TextView textView, String selectionString, String fullString, int selectionColor) {
        if (textView != null) {
            ForegroundColorSpan selection = new ForegroundColorSpan(selectionColor);
            SpannableStringBuilder builder = new SpannableStringBuilder(fullString);
            int index = fullString.indexOf(selectionString);
            if (index != -1) {
                builder.setSpan(selection, index, index + selectionString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
            } else {
                textView.setText(fullString);
            }
        }
    }

    /**
     * 设置颜色的透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int setColorAlpha(@ColorInt int color, float alpha) {
        return setColorAlpha(color, alpha, true);
    }

    /**
     * 设置颜色的alpha值
     *
     * @param color    需要被设置的颜色值
     * @param alpha    取值为[0,1]，0表示全透明，1表示不透明
     * @param override 覆盖原本的 alpha
     * @return 返回改变了 alpha 值的颜色值
     */
    public static int setColorAlpha(@ColorInt int color, float alpha, boolean override) {
        int origin = override ? 0xff : (color >> 24) & 0xff;
        return color & 0x00ffffff | (int) (alpha * origin) << 24;
    }

    /**
     * 根据比例，在两个color值之间计算出一个color值
     * <b>注意该方法是ARGB通道分开计算比例的</b>
     *
     * @param fromColor 开始的color值
     * @param toColor   最终的color值
     * @param fraction  比例，取值为[0,1]，为0时返回 fromColor， 为1时返回 toColor
     * @return 计算出的color值
     */
    public static int computeColor(@ColorInt int fromColor, @ColorInt int toColor, float fraction) {
        fraction = Math.max(Math.min(fraction, 1), 0);

        int minColorA = Color.alpha(fromColor);
        int maxColorA = Color.alpha(toColor);
        int resultA = (int) ((maxColorA - minColorA) * fraction) + minColorA;

        int minColorR = Color.red(fromColor);
        int maxColorR = Color.red(toColor);
        int resultR = (int) ((maxColorR - minColorR) * fraction) + minColorR;

        int minColorG = Color.green(fromColor);
        int maxColorG = Color.green(toColor);
        int resultG = (int) ((maxColorG - minColorG) * fraction) + minColorG;

        int minColorB = Color.blue(fromColor);
        int maxColorB = Color.blue(toColor);
        int resultB = (int) ((maxColorB - minColorB) * fraction) + minColorB;

        return Color.argb(resultA, resultR, resultG, resultB);
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色值
     * @return 转换后的字符串
     */
    public static String colorToString(@ColorInt int color) {
        return String.format("#%08X", color);
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
    
}
