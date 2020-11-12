package me.zhouzhuo810.magpiex.utils;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Drawable相关工具类
 *
 * @author zhouzhuo810
 */
public class DrawableUtil {
    
    /**
     * 代码生成CheckBox的selector。
     *
     * @param idNormal      没选中的图片
     * @param idChecked     选中的图片
     * @param colorChecked  选中的颜色
     * @return StateListDrawable
     */
    public static StateListDrawable newCheckSelector(@DrawableRes int idNormal,@DrawableRes int idChecked,@ColorInt int colorChecked) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : SimpleUtil.getDrawable(idNormal);
        Drawable checked = idChecked == -1 ? null : SimpleUtil.getDrawable(idChecked);
        if (checked != null) {
            try {
                checked = checked.mutate();
                setDrawableColor(checked, colorChecked);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bg.addState(new int[]{android.R.attr.state_checked}, checked);
        bg.addState(new int[]{android.R.attr.state_selected}, checked);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }
    
    /**
     * 设置Drawable的颜色
     *
     * @param drawable  Drawable
     * @param color     颜色值
     */
    public static void setDrawableColor(@NonNull Drawable drawable, @ColorInt int color) {
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
