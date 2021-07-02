package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;
import me.zhouzhuo810.magpiex.utils.ViewUtil;

/**
 * 屏幕适配工具类
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:44 PM
 */
public class LoadViewHelper extends AbsLoadViewHelper {
    
    public LoadViewHelper(Context context, String adaptType, int designWidth, int designHeight, int designDpi,
                          float fontSize, String unit) {
        super(context, adaptType, designWidth, designHeight, designDpi, fontSize, unit);
    }
    
    @Override
    public void loadWidthHeight(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int originalWidth = layoutParams.width;
        int originalHeight = layoutParams.height;
        if (originalWidth > 0 && originalHeight > 0) {
            if (originalWidth == originalHeight) {
                layoutParams.width = setValue(originalWidth, true, false);
                layoutParams.height = setValue(originalHeight, false, false);
            } else {
                layoutParams.width = setValue(originalWidth, true, false);
                layoutParams.height = setValue(originalHeight, false, false);
            }
        } else if (originalWidth > 0) {
            layoutParams.width = setValue(originalWidth, true, false);
        } else if (originalHeight > 0) {
            layoutParams.height = setValue(originalHeight, false, false);
        }
    }
    
    @Override
    public void loadFontSize(View view) {
        if ((view instanceof TextView)) {
            TextView textView = (TextView) view;
            textView.setTextSize(0, setFontSize(textView));
        }
    }
    
    private float setFontSize(TextView textView) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType) || ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return isLandscape() ? calculateValueByHeight(textView.getTextSize(), true) : calculateValueByWidth(textView.getTextSize(), true);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return calculateValueByWidth(textView.getTextSize(), true);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return calculateValueByHeight(textView.getTextSize(), true);
        } else {
            return calculateValueByWidth(textView.getTextSize(), true);
        }
    }
    
    @Override
    public void loadPadding(View view) {
        view.setPadding(setValue(view.getPaddingLeft(), true, false), setValue(view.getPaddingTop(), false, false),
            setValue(view.getPaddingRight(), true, false), setValue(view.getPaddingBottom(), false, false));
    }
    
    @Override
    public void loadLayoutMargin(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin, true, false);
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin, true, false);
            marginLayoutParams.topMargin = setValue(marginLayoutParams.topMargin, false, false);
            marginLayoutParams.bottomMargin = setValue(marginLayoutParams.bottomMargin, false, false);
            view.setLayoutParams(marginLayoutParams);
        }
    }
    
    @Override
    public void loadMaxWidthAndHeight(View view) {
        ViewUtil.setMaxWidth(view, setValue(ViewUtil.getMaxWidth(view), true, false));
        ViewUtil.setMaxHeight(view, setValue(ViewUtil.getMaxWidth(view), false, false));
    }
    
    @Override
    public void loadMinWidthAndHeight(View view) {
        ViewUtil.setMinWidth(view, setValue(ViewUtil.getMinWidth(view), true, false));
        ViewUtil.setMinHeight(view, setValue(ViewUtil.getMinHeight(view), false, false));
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            ViewUtil.setTextViewMinWidth(tv, setValue(ViewUtil.getTextViewMinWidth(tv), true, false));
            ViewUtil.setTextViewMinHeight(tv, setValue(ViewUtil.getTextViewMinHeight(tv), false, false));
            if (tv.getMaxWidth() != Integer.MAX_VALUE && tv.getMaxWidth() > 0) {
                tv.setMaxWidth(setValue(tv.getMaxWidth(), true, false));
            }
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (iv.getMaxWidth() != Integer.MAX_VALUE && iv.getMaxWidth() > 0) {
                iv.setMaxWidth(setValue(iv.getMaxWidth(), true, false));
            }
        } else if (view instanceof ConstraintLayout) {
            ConstraintLayout sl = (ConstraintLayout) view;
            sl.setMinWidth(setValue(sl.getMinWidth(), true, false));
            sl.setMinHeight(setValue(sl.getMinHeight(), false, false));
        }
    }
    
    @Override
    public void loadCustomAttrs(View view) {
        loadConstraintLayout(view);
        loadFloatingActionButton(view);
    }
    
    private void loadFloatingActionButton(View view) {
        if (view instanceof FloatingActionButton) {
            FloatingActionButton btn = (FloatingActionButton) view;
            int customSize = btn.getCustomSize();
            if (customSize > FloatingActionButton.NO_CUSTOM_SIZE) {
                btn.setCustomSize(setValue(customSize, isLandscape(), false));
            }
        }
    }
    
    private void loadConstraintLayout(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams cp = (ConstraintLayout.LayoutParams) params;
            int orientation = cp.orientation;
            if (view instanceof Guideline) {
                if (cp.guideBegin != -1) {
                    cp.guideBegin = setValue(cp.guideBegin, orientation == ConstraintLayout.LayoutParams.HORIZONTAL, false);
                }
                if (cp.guideEnd != -1) {
                    cp.guideEnd = setValue(cp.guideEnd, orientation == ConstraintLayout.LayoutParams.HORIZONTAL, false);
                }
            }
            cp.matchConstraintMinWidth = setValue(cp.matchConstraintMinWidth, true, false);
            cp.matchConstraintMinHeight = setValue(cp.matchConstraintMinHeight, false, false);
            cp.matchConstraintMaxWidth = setValue(cp.matchConstraintMaxWidth, true, false);
            cp.matchConstraintMaxHeight = setValue(cp.matchConstraintMaxHeight, false, false);
            cp.goneLeftMargin = setValue(cp.goneLeftMargin, true, false);
            cp.goneRightMargin = setValue(cp.goneRightMargin, true, false);
            cp.goneStartMargin = setValue(cp.goneStartMargin, true, false);
            cp.goneEndMargin = setValue(cp.goneEndMargin, true, false);
            cp.goneTopMargin = setValue(cp.goneTopMargin, false, false);
            cp.goneBottomMargin = setValue(cp.goneBottomMargin, false, false);
            cp.circleRadius = setValue(cp.circleRadius, isLandscape(), false);
            view.setLayoutParams(cp);
        }
        if (view instanceof Barrier) {
            Barrier barrier = (Barrier) view;
            int type = barrier.getType();
            if (barrier.getMargin() != 0) {
                switch (type) {
                    case Barrier.LEFT:
                    case Barrier.RIGHT:
                    case Barrier.START:
                    case Barrier.END:
                        barrier.setMargin(setValue(barrier.getMargin(), true, false));
                        break;
                    case Barrier.TOP:
                    case Barrier.BOTTOM:
                        barrier.setMargin(setValue(barrier.getMargin(), false, false));
                        break;
                }
            }
        }
    }
    
    
    private int setValue(int value, boolean horizontal, boolean isFontSize) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value, isFontSize) : setValueByWidth(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return horizontal ? (isLandscape() ? setValueByHeight(value, isFontSize)
                : setValueByWidth(value, isFontSize)) : (isLandscape() ? setValueByWidth(value, isFontSize) : setValueByHeight(value, isFontSize));
        }
        return setValueByWidth(value, isFontSize);
    }
    
    private float setValue(float value, boolean horizontal, boolean isFontSize) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value, isFontSize) : setValueByWidth(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value, isFontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return horizontal ? (isLandscape() ? setValueByHeight(value, isFontSize) : setValueByWidth(value, isFontSize)) :
                (isLandscape() ? setValueByWidth(value, isFontSize) : setValueByHeight(value, isFontSize));
        }
        return setValueByWidth(value, isFontSize);
    }
    
    
    private int setValueByWidth(int value, boolean isFontSize) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValueByWidth(value, isFontSize);
    }
    
    private float setValueByWidth(float value, boolean isFontSize) {
        return calculateValueByWidth(value, isFontSize);
    }
    
    private int setValueByHeight(int value, boolean isFontSize) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValueByHeight(value, isFontSize);
    }
    
    private float setValueByHeight(float value, boolean isFontSize) {
        return calculateValueByHeight(value, isFontSize);
    }
    
    private float calculateValueByWidth(float value, boolean isFontSize) {
        if (disableAdapt) {
            return value;
        }
        if ("px".equals(unit)) {
            return (isFontSize ? fontSize : 1f) * value * ((isLandscape() ? actualHeight : actualWidth) / (float) designWidth);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return (isFontSize ? fontSize : 1f) * value * ((isLandscape() ? actualHeight : actualWidth) / (float) designWidth);
        }
        return 0;
    }
    
    private boolean isLandscape() {
        return screenOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }
    
    private float calculateValueByHeight(float value, boolean isFontSize) {
        if (disableAdapt) {
            return value;
        }
        if ("px".equals(unit)) {
            return (isFontSize ? fontSize : 1f) * value * ((isLandscape() ? actualWidth : actualHeight) / (float) designHeight);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return (isFontSize ? fontSize : 1f) * value * ((isLandscape() ? actualWidth : actualHeight) / (float) designHeight);
        }
        return 0;
    }
    
    private int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }
    
    
    @Override
    public int getScaledValue(int px, boolean isFontSize) {
        return setValue(px, isLandscape(), isFontSize);
    }
    
    @Override
    public float getScaledValue(float px, boolean isFontSize) {
        return setValue(px, isLandscape(), isFontSize);
    }
    
    @Override
    public int getScaledValue(int px, boolean horizontal, boolean isFontSize) {
        return setValue(px, horizontal, isFontSize);
    }
    
    @Override
    public float getScaledValue(float px, boolean horizontal, boolean isFontSize) {
        return setValue(px, horizontal, isFontSize);
    }
    
    @Override
    public int getScaledValueByWidth(int px, boolean isFontSize) {
        return setValueByWidth(px, isFontSize);
    }
    
    @Override
    public float getScaledValueByWidth(float px, boolean isFontSize) {
        return setValueByWidth(px, isFontSize);
    }
    
    @Override
    public int getScaledValueByHeight(int px, boolean isFontSize) {
        return setValueByHeight(px, isFontSize);
    }
    
    @Override
    public float getScaledValueByHeight(float px, boolean isFontSize) {
        return setValueByHeight(px, isFontSize);
    }
}
