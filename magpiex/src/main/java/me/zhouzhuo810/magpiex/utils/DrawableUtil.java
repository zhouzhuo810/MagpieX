package me.zhouzhuo810.magpiex.utils;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;

/**
 * Drawable相关工具类
 *
 * @author zhouzhuo810
 */
public class DrawableUtil {
    
    /**
     * 代码生成CheckBox的selector。
     *
     * @param idNormal     没选中的图片
     * @param idChecked    选中的图片
     * @param colorChecked 选中的颜色
     * @return StateListDrawable
     */
    public static StateListDrawable newCheckSelector(@DrawableRes int idNormal, @DrawableRes int idChecked, @ColorInt int colorChecked) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : SimpleUtil.getDrawable(idNormal);
        Drawable checked = idChecked == -1 ? null : SimpleUtil.getDrawable(idChecked);
        if (checked != null) {
            try {
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
     * @param drawable Drawable
     * @param color    颜色值
     */
    public static void setDrawableColor(@NonNull Drawable drawable, @ColorInt int color) {
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable.mutate()).setColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
    
    /**
     * 圆角矩形背景 Shape
     *
     * @param radius 圆角半径
     * @param color  颜色
     */
    public static GradientDrawable roundShape(int radius, int color) {
        return roundShape(radius, color, 0, 0, 0, 0);
    }
    
    /**
     * 圆角矩形背景 Shape
     *
     * @param radius      圆角半径
     * @param color       填充颜色
     * @param strokeColor 边框颜色
     */
    public static GradientDrawable roundShape(int radius, int color, int strokeColor) {
        return roundShape(radius, color, strokeColor, 2, 0, 0);
    }
    
    /**
     * 圆角矩形背景 Shape
     *
     * @param radius      圆角半径
     * @param color       填充颜色
     * @param strokeColor 边框颜色
     * @param strokeWidth 边框宽度
     */
    
    public static GradientDrawable roundShape(int radius, int color, int strokeColor, int strokeWidth) {
        return roundShape(radius, color, strokeColor, strokeWidth, 0, 0);
    }
    
    /**
     * 圆角矩形背景 Shape
     *
     * @param radius      圆角半径
     * @param color       填充颜色
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @param dashWidth   边框虚线长度
     * @param dashGap     边框虚线空隙
     */
    public static GradientDrawable roundShape(int radius, int color, int strokeColor, int strokeWidth, int dashWidth, int dashGap) {
        return new ShapeBuilder()
            .shape(GradientDrawable.RECTANGLE)
            .radius(radius)
            .color(color)
            .strokeColor(strokeColor)
            .strokeWidth(strokeWidth)
            .strokeDashWidth(dashWidth)
            .strokeDashGap(dashGap)
            .build();
    }
    
    /**
     * 渐变背景
     *
     * @param radius    圆角
     * @param fromColor 渐变开始颜色
     * @param toColor   渐变结束颜色
     */
    public static GradientDrawable gradientShape(int radius, int fromColor, int toColor) {
        return gradientShape(radius, fromColor, toColor, GradientDrawable.Orientation.LEFT_RIGHT, 0, 0, 0, 0);
    }
    
    /**
     * 渐变背景
     *
     * @param radius      圆角
     * @param fromColor   渐变开始颜色
     * @param toColor     渐变结束颜色
     * @param orientation 渐变方向
     */
    public static GradientDrawable gradientShape(int radius, int fromColor, int toColor, GradientDrawable.Orientation orientation) {
        return gradientShape(radius, fromColor, toColor, orientation, 0, 0, 0, 0);
    }
    
    public static GradientDrawable gradientShape(int radius, int fromColor, int toColor, GradientDrawable.Orientation orientation, int strokeColor, int strokeWidth, int dashWidth, int dashGap) {
        return new ShapeBuilder()
            .shape(GradientDrawable.RECTANGLE)
            .radius(radius)
            .gradient(true)
            .fromColor(fromColor)
            .toColor(toColor)
            .gradientOrientation(orientation)
            .strokeColor(strokeColor)
            .strokeWidth(strokeWidth)
            .strokeDashWidth(dashWidth)
            .strokeDashGap(dashGap)
            .build();
    }
    
    public static class ShapeBuilder {
        
        private int fromColor;
        private int toColor;
        private boolean gradient;
        private GradientDrawable.Orientation gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
        private int gradientType;
        private float topLeftRadius;
        private float topRightRadius;
        private float bottomLeftRadius;
        private float bottomRightRadius;
        private int strokeWidth;
        private int strokeColor;
        private int strokeDashWidth;
        private int strokeDashGap;
        private int shape = GradientDrawable.RECTANGLE;
        private int color;
        private int ringThickness;
        private int alpha = 0xFF;
        
        public ShapeBuilder() {
        }
        
        /**
         * 设置 shape
         * <br>
         * {@link GradientDrawable#RECTANGLE}
         * <br>
         * {@link GradientDrawable#OVAL}
         * <br>
         * {@link GradientDrawable#LINE}
         * <br>
         * {@link GradientDrawable#RING}
         */
        public ShapeBuilder shape(int shape) {
            this.shape = shape;
            return this;
        }
        
        /**
         * 填充颜色
         *
         * @param color 颜色值
         */
        public ShapeBuilder color(@ColorInt int color) {
            this.color = color;
            return this;
        }
        
        /**
         * 边框颜色
         *
         * @param strokeColor 颜色值
         */
        public ShapeBuilder strokeColor(@ColorInt int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }
        
        /**
         * 边框宽度
         *
         * @param strokeWidth 宽度
         */
        public ShapeBuilder strokeWidth(@Px int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }
        
        /**
         * 虚线边框的线宽
         *
         * @param strokeDashWidth 宽度
         */
        public ShapeBuilder strokeDashWidth(@Px int strokeDashWidth) {
            this.strokeDashWidth = strokeDashWidth;
            return this;
        }
        
        /**
         * 虚线边框的空隙宽度
         *
         * @param strokeDashGap 宽度
         */
        public ShapeBuilder strokeDashGap(@Px int strokeDashGap) {
            this.strokeDashGap = strokeDashGap;
            return this;
        }
        
        /**
         * 左上角圆角半径
         *
         * @param topLeftRadius 半径
         */
        public ShapeBuilder topLeftRadius(float topLeftRadius) {
            this.topLeftRadius = topLeftRadius;
            return this;
        }
        
        /**
         * 右上角圆角半径
         *
         * @param topRightRadius 半径
         */
        public ShapeBuilder topRightRadius(float topRightRadius) {
            this.topRightRadius = topRightRadius;
            return this;
        }
        
        /**
         * 左下角圆角半径
         *
         * @param bottomLeftRadius 半径
         */
        public ShapeBuilder bottomLeftRadius(float bottomLeftRadius) {
            this.bottomLeftRadius = bottomLeftRadius;
            return this;
        }
        
        /**
         * 右下角圆角半径
         *
         * @param bottomRightRadius 半径
         */
        public ShapeBuilder bottomRightRadius(float bottomRightRadius) {
            this.bottomRightRadius = bottomRightRadius;
            return this;
        }
        
        /**
         * 半径
         *
         * @param radius 半径
         */
        public ShapeBuilder radius(float radius) {
            this.topLeftRadius = radius;
            this.topRightRadius = radius;
            this.bottomLeftRadius = radius;
            this.bottomRightRadius = radius;
            return this;
        }
        
        
        /**
         * 透明度
         *
         * @param alpha 透明度，0-255
         */
        public ShapeBuilder alpha(int alpha) {
            this.alpha = alpha;
            return this;
        }
        
        
        /**
         * 是否渐变
         *
         * @param gradient 是否
         */
        public ShapeBuilder gradient(boolean gradient) {
            this.gradient = gradient;
            return this;
        }
        
        /**
         * 渐变开始颜色
         *
         * @param fromColor 颜色值
         */
        public ShapeBuilder fromColor(@ColorInt int fromColor) {
            this.fromColor = fromColor;
            return this;
        }
        
        /**
         * 渐变结束颜色
         *
         * @param toColor 颜色值
         */
        public ShapeBuilder toColor(@ColorInt int toColor) {
            this.toColor = toColor;
            return this;
        }
        
        /**
         * 圆环的厚度
         *
         * @param ringThickness 厚度
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        public ShapeBuilder ringThickness(@Px int ringThickness) {
            this.ringThickness = ringThickness;
            return this;
        }
        
        /**
         * 渐变类型
         * <br>
         * {@link GradientDrawable#LINEAR_GRADIENT}
         * <br>
         * {@link GradientDrawable#RADIAL_GRADIENT}
         * <br>
         * {@link GradientDrawable#SWEEP_GRADIENT}
         *
         * @param gradientType 类型
         */
        public ShapeBuilder gradientType(int gradientType) {
            this.gradientType = gradientType;
            return this;
        }
        
        /**
         * 渐变方向
         *
         * @param gradientOrientation 方向
         */
        public ShapeBuilder gradientOrientation(GradientDrawable.Orientation gradientOrientation) {
            this.gradientOrientation = gradientOrientation;
            return this;
        }
        
        /**
         * 生成 GradientDrawable
         *
         * @return GradientDrawable
         */
        public GradientDrawable build() {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(color);
            drawable.setShape(shape);
            drawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
            if (strokeWidth > 0) {
                drawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap);
            }
            drawable.setOrientation(gradientOrientation == null ? GradientDrawable.Orientation.LEFT_RIGHT : gradientOrientation);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setThickness(ringThickness);
            }
            drawable.setGradientType(gradientType);
            drawable.setAlpha(alpha);
            if (gradient) {
                drawable.setColors(new int[]{fromColor, toColor});
            }
            drawable.setDither(true);
            return drawable;
        }
        
        /**
         * 应用到控件
         *
         * @param view View
         */
        public void applyTo(View view) {
            if (view != null) {
                view.setBackground(build());
            }
        }
        
    }
}
