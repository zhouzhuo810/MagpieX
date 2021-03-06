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
                layoutParams.width = setValue(originalWidth);
                layoutParams.height = setValue(originalHeight);
            } else {
                layoutParams.width = setValue(originalWidth);
                layoutParams.height = setValue(originalHeight);
            }
        } else if (originalWidth > 0) {
            layoutParams.width = setValue(originalWidth);
        } else if (originalHeight > 0) {
            layoutParams.height = setValue(originalHeight);
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
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
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
        view.setPadding(setValue(view.getPaddingLeft()), setValue(view.getPaddingTop()),
            setValue(view.getPaddingRight()), setValue(view.getPaddingBottom()));
    }
    
    @Override
    public void loadLayoutMargin(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin);
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin);
            marginLayoutParams.topMargin = setValue(marginLayoutParams.topMargin);
            marginLayoutParams.bottomMargin = setValue(marginLayoutParams.bottomMargin);
            view.setLayoutParams(marginLayoutParams);
        }
    }
    
    @Override
    public void loadMaxWidthAndHeight(View view) {
        ViewUtil.setMaxWidth(view, setValue(ViewUtil.getMaxWidth(view)));
        ViewUtil.setMaxHeight(view, setValue(ViewUtil.getMaxWidth(view)));
    }
    
    @Override
    public void loadMinWidthAndHeight(View view) {
        ViewUtil.setMinWidth(view, setValue(ViewUtil.getMinWidth(view)));
        ViewUtil.setMinHeight(view, setValue(ViewUtil.getMinHeight(view)));
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            ViewUtil.setTextViewMinWidth(tv, setValue(ViewUtil.getTextViewMinWidth(tv)));
            ViewUtil.setTextViewMinHeight(tv, setValue(ViewUtil.getTextViewMinHeight(tv)));
            if (tv.getMaxWidth() != Integer.MAX_VALUE && tv.getMaxWidth() > 0) {
                tv.setMaxWidth(setValue(tv.getMaxWidth()));
            }
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (iv.getMaxWidth() != Integer.MAX_VALUE && iv.getMaxWidth() > 0) {
                iv.setMaxWidth(setValue(iv.getMaxWidth()));
            }
        } else if (view instanceof ConstraintLayout) {
            ConstraintLayout sl = (ConstraintLayout) view;
            sl.setMinHeight(setValue(sl.getMinHeight()));
            sl.setMinWidth(setValue(sl.getMinWidth()));
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
            if (customSize > FloatingActionButton.NO_CUSTOM_SIZE ) {
                btn.setCustomSize(setValue(customSize));
            }
        }
    }
    
    private void loadConstraintLayout(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams cp = (ConstraintLayout.LayoutParams) params;
            if (view instanceof Guideline) {
                if (cp.guideBegin != -1) {
                    cp.guideBegin = setValue(cp.guideBegin);
                }
                if (cp.guideEnd != -1) {
                    cp.guideEnd = setValue(cp.guideEnd);
                }
            }
            cp.matchConstraintMinHeight = setValue(cp.matchConstraintMinHeight);
            cp.matchConstraintMinWidth = setValue(cp.matchConstraintMinWidth);
            cp.matchConstraintMaxWidth = setValue(cp.matchConstraintMaxWidth);
            cp.matchConstraintMaxHeight = setValue(cp.matchConstraintMaxHeight);
            cp.goneBottomMargin = setValue(cp.goneBottomMargin);
            cp.goneEndMargin = setValue(cp.goneEndMargin);
            cp.goneLeftMargin = setValue(cp.goneLeftMargin);
            cp.goneRightMargin = setValue(cp.goneRightMargin);
            cp.goneStartMargin = setValue(cp.goneStartMargin);
            cp.goneTopMargin = setValue(cp.goneTopMargin);
            cp.circleRadius = setValue(cp.circleRadius);
            view.setLayoutParams(cp);
        }
        if (view instanceof Barrier) {
            Barrier barrier = (Barrier) view;
            barrier.setMargin(setValue(barrier.getMargin()));
        }
    }
    
    
    private int setValue(int value) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value) : setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value);
        }
        return setValueByWidth(value);
    }
    
    private float setValue(float value) {
        if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_AUTO.equals(adaptType)) {
            return isLandscape() ? setValueByHeight(value) : setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_WIDTH.equals(adaptType)) {
            return setValueByWidth(value);
        } else if (ScreenAdapterUtil.SCREEN_ADAPT_TYPE_HEIGHT.equals(adaptType)) {
            return setValueByHeight(value);
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
        return setValue(px);
    }
    
    @Override
    public float getScaledValue(float px) {
        return setValue(px);
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
