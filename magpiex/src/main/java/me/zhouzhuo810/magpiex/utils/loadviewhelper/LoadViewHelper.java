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
 * Created by yatoooon on 2018/2/6.
 */

public class LoadViewHelper extends AbsLoadViewHelper {
    
    public LoadViewHelper(Context context, String adaptType, int designWidth, int designHeight, int designDpi,
                          float fontSize, String unit) {
        super(context, adaptType, designWidth, designHeight, designDpi, fontSize, unit);
    }
    
    @Override
    public void loadWidthHeightFont(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int originalWidth = layoutParams.width;
        int originalHeight = layoutParams.height;
        if (originalWidth > 0 && originalHeight > 0) {
            if (originalWidth == originalHeight) {
                layoutParams.width = setValue(originalWidth, true);
                layoutParams.height = setValue(originalHeight, false);
            } else {
                layoutParams.width = setValue(originalWidth, true);
                layoutParams.height = setValue(originalHeight, false);
            }
        } else if (originalWidth > 0) {
            layoutParams.width = setValue(originalWidth, true);
        } else if (originalHeight > 0) {
            layoutParams.height = setValue(originalHeight, false);
        }
        loadViewFont(view);
    }
    
    private void loadViewFont(View view) {
        if ((view instanceof TextView)) {
            TextView textView = (TextView) view;
            textView.setTextSize(0, setFontSize(textView));
        }
    }
    
    private float setFontSize(TextView textView) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType) || ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return isLandscape() ? calculateValueByHeight(textView.getTextSize() * fontSize) : calculateValueByWidth(textView.getTextSize() * fontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return calculateValueByWidth(textView.getTextSize() * fontSize);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return calculateValueByHeight(textView.getTextSize() * fontSize);
        } else {
            return calculateValueByWidth(textView.getTextSize() * fontSize);
        }
    }
    
    @Override
    public void loadPadding(View view) {
        view.setPadding(setValue(view.getPaddingLeft(), true), setValue(view.getPaddingTop(), false),
            setValue(view.getPaddingRight(), true), setValue(view.getPaddingBottom(), false));
    }
    
    @Override
    public void loadLayoutMargin(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin, true);
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin, true);
            marginLayoutParams.topMargin = setValue(marginLayoutParams.topMargin, false);
            marginLayoutParams.bottomMargin = setValue(marginLayoutParams.bottomMargin, false);
            view.setLayoutParams(marginLayoutParams);
        }
    }
    
    @Override
    public void loadMaxWidthAndHeight(View view) {
        ViewUtil.setMaxWidth(view, setValue(ViewUtil.getMaxWidth(view), true));
        ViewUtil.setMaxHeight(view, setValue(ViewUtil.getMaxWidth(view), false));
    }
    
    @Override
    public void loadMinWidthAndHeight(View view) {
        ViewUtil.setMinWidth(view, setValue(ViewUtil.getMinWidth(view), true));
        ViewUtil.setMinHeight(view, setValue(ViewUtil.getMinHeight(view), false));
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            ViewUtil.setTextViewMinWidth(tv, setValue(ViewUtil.getTextViewMinWidth(tv), true));
            ViewUtil.setTextViewMinHeight(tv, setValue(ViewUtil.getTextViewMinHeight(tv), false));
            if (tv.getMaxWidth() != Integer.MAX_VALUE && tv.getMaxWidth() > 0) {
                tv.setMaxWidth(setValue(tv.getMaxWidth(), true));
            }
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (iv.getMaxWidth() != Integer.MAX_VALUE && iv.getMaxWidth() > 0) {
                iv.setMaxWidth(setValue(iv.getMaxWidth(), true));
            }
        } else if (view instanceof ConstraintLayout) {
            ConstraintLayout sl = (ConstraintLayout) view;
            sl.setMinWidth(setValue(sl.getMinWidth(), true));
            sl.setMinHeight(setValue(sl.getMinHeight(), false));
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
                btn.setCustomSize(setValue(customSize, isLandscape()));
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
                    cp.guideBegin = setValue(cp.guideBegin, orientation == ConstraintLayout.LayoutParams.HORIZONTAL);
                }
                if (cp.guideEnd != -1) {
                    cp.guideEnd = setValue(cp.guideEnd, orientation == ConstraintLayout.LayoutParams.HORIZONTAL);
                }
            }
            cp.matchConstraintMinWidth = setValue(cp.matchConstraintMinWidth, true);
            cp.matchConstraintMinHeight = setValue(cp.matchConstraintMinHeight, false);
            cp.matchConstraintMaxWidth = setValue(cp.matchConstraintMaxWidth, true);
            cp.matchConstraintMaxHeight = setValue(cp.matchConstraintMaxHeight, false);
            cp.goneLeftMargin = setValue(cp.goneLeftMargin, true);
            cp.goneRightMargin = setValue(cp.goneRightMargin, true);
            cp.goneStartMargin = setValue(cp.goneStartMargin, true);
            cp.goneEndMargin = setValue(cp.goneEndMargin, true);
            cp.goneTopMargin = setValue(cp.goneTopMargin, false);
            cp.goneBottomMargin = setValue(cp.goneBottomMargin, false);
            cp.circleRadius = setValue(cp.circleRadius, isLandscape());
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
                        barrier.setMargin(setValue(barrier.getMargin(), true));
                        break;
                    case Barrier.TOP:
                    case Barrier.BOTTOM:
                        barrier.setMargin(setValue(barrier.getMargin(), false));
                        break;
                }
            }
        }
    }
    
    
    private int setValue(int value, boolean horizontal) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value) : setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return horizontal ? (isLandscape() ? setValueByHeight(value) : setValueByWidth(value)) : (isLandscape() ? setValueByWidth(value) : setValueByHeight(value));
        }
        return setValueByWidth(value);
    }
    
    private float setValue(float value, boolean horizontal) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value) : setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WH.equals(adaptType)) {
            return horizontal ? (isLandscape() ? setValueByHeight(value) : setValueByWidth(value)) : (isLandscape() ? setValueByWidth(value) : setValueByHeight(value));
        }
        return setValueByWidth(value);
    }
    
    
    private int setValueByWidth(int value) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValueByWidth(value);
    }
    
    private float setValueByWidth(float value) {
        return calculateValueByWidth(value);
    }
    
    private int setValueByHeight(int value) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValueByHeight(value);
    }
    
    private float setValueByHeight(float value) {
        return calculateValueByHeight(value);
    }
    
    private float calculateValueByWidth(float value) {
        if ("px".equals(unit)) {
            return value * ((isLandscape() ? actualHeight : actualWidth) / (float) designWidth);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return value * ((isLandscape() ? actualHeight : actualWidth) / (float) designWidth);
        }
        return 0;
    }
    
    private boolean isLandscape() {
        return screenOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }
    
    private float calculateValueByHeight(float value) {
        if ("px".equals(unit)) {
            return value * ((isLandscape() ? actualWidth : actualHeight) / (float) designHeight);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return value * ((isLandscape() ? actualWidth : actualHeight) / (float) designHeight);
        }
        return 0;
    }
    
    private int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }
    
    
    @Override
    public int getScaledValue(int px) {
        return setValue(px, isLandscape());
    }
    
    @Override
    public float getScaledValue(float px) {
        return setValue(px, isLandscape());
    }
    
    @Override
    public int getScaledValue(int px, boolean horizontal) {
        return setValue(px, horizontal);
    }
    
    @Override
    public float getScaledValue(float px, boolean horizontal) {
        return setValue(px, horizontal);
    }
    
    @Override
    public int getScaledValueByWidth(int px) {
        return setValueByWidth(px);
    }
    
    @Override
    public float getScaledValueByWidth(float px) {
        return setValueByWidth(px);
    }
    
    @Override
    public int getScaledValueByHeight(int px) {
        return setValueByHeight(px);
    }
    
    @Override
    public float getScaledValueByHeight(float px) {
        return setValueByHeight(px);
    }
}
