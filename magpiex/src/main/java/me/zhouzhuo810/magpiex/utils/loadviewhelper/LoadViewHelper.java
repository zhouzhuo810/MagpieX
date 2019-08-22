package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.zhouzhuo810.magpiex.utils.ViewUtil;

/**
 * Created by yatoooon on 2018/2/6.
 */

public class LoadViewHelper extends AbsLoadViewHelper {
    
    public LoadViewHelper(Context context, boolean scaleWidthAndHeight, int designWidth, int designHeight, int designDpi,
                          float fontSize, String unit) {
        super(context, scaleWidthAndHeight, designWidth, designHeight, designDpi, fontSize, unit);
    }
    
    @Override
    public void loadWidthHeightFont(View view, boolean forceWidthHeight) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (forceWidthHeight) {
            if (layoutParams.width > 0) {
                layoutParams.width = setValue(layoutParams.width);
            }
            if (layoutParams.height > 0) {
                layoutParams.height = setValueByHeight(layoutParams.height);
            }
        } else {
            if (layoutParams.width > 0) {
                //宽度大于0
                if (layoutParams.height > 0) {
                    //且高度大于0
                    if (layoutParams.height == layoutParams.width) {
                        //如果宽高相等，则只按宽度来，保持原始比例，缩放宽高。
                        layoutParams.width = setValue(layoutParams.width);
                        layoutParams.height = setValue(layoutParams.height);
                    } else {
                        //如果宽高不一致，缩放宽高
                        layoutParams.width = setValue(layoutParams.width);
                        layoutParams.height = scaleWidthAndHeight ? setValueByHeight(layoutParams.height) :
                            setValue(layoutParams.height);
                    }
                } else {
                    //宽度大于0，高度不大于0，缩放宽
                    layoutParams.width = setValue(layoutParams.width);
                }
            } else {
                //宽高不大于0，高度大于0，缩放高
                if (layoutParams.height > 0) {
                    layoutParams.height = scaleWidthAndHeight ? setValueByHeight(layoutParams.height) :
                        setValue(layoutParams.height);
                }
            }
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
        return calculateValue(textView.getTextSize() * fontSize);
    }
    
    @Override
    public void loadPadding(View view, boolean forceWidthHeight) {
        if (forceWidthHeight) {
            view.setPadding(setValue(view.getPaddingLeft()), setValueByHeight(view.getPaddingTop()),
                setValue(view.getPaddingRight()), setValueByHeight(view.getPaddingBottom()));
        } else {
            view.setPadding(setValue(view.getPaddingLeft()), scaleWidthAndHeight ? setValueByHeight(view.getPaddingTop()) :
                    setValue(view.getPaddingTop()),
                setValue(view.getPaddingRight()), scaleWidthAndHeight ? setValueByHeight(view.getPaddingBottom()) :
                    setValue(view.getPaddingBottom()));
        }
    }
    
    @Override
    public void loadLayoutMargin(View view, boolean forceWidthHeight) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin);
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin);
            if (forceWidthHeight) {
                marginLayoutParams.topMargin = setValueByHeight(marginLayoutParams.topMargin);
                marginLayoutParams.bottomMargin = setValueByHeight(marginLayoutParams.bottomMargin);
                view.setLayoutParams(marginLayoutParams);
            } else {
                marginLayoutParams.topMargin = scaleWidthAndHeight ? setValueByHeight(marginLayoutParams.topMargin) :
                    setValue(marginLayoutParams.topMargin);
                marginLayoutParams.bottomMargin = scaleWidthAndHeight ? setValueByHeight(marginLayoutParams.bottomMargin) :
                    setValue(marginLayoutParams.bottomMargin);
                view.setLayoutParams(marginLayoutParams);
            }
        }
    }
    
    @Override
    public void loadMaxWidthAndHeight(View view, boolean forceWidthHeight) {
        ViewUtil.setMaxWidth(view, setValue(ViewUtil.getMaxWidth(view)));
        if (forceWidthHeight) {
            ViewUtil.setMaxHeight(view, setValueByHeight(ViewUtil.getMaxHeight(view)));
        } else {
            ViewUtil.setMaxHeight(view, scaleWidthAndHeight ? setValueByHeight(ViewUtil.getMaxHeight(view)) :
                setValue(ViewUtil.getMaxHeight(view)));
        }
    }
    
    @Override
    public void loadMinWidthAndHeight(View view, boolean forceWidthHeight) {
        ViewUtil.setMinWidth(view, setValue(ViewUtil.getMinWidth(view)));
        if (forceWidthHeight) {
            ViewUtil.setMinHeight(view, setValueByHeight(ViewUtil.getMinHeight(view)));
        } else {
            ViewUtil.setMinHeight(view, scaleWidthAndHeight ? setValueByHeight(ViewUtil.getMinHeight(view)) :
                setValue(ViewUtil.getMinHeight(view)));
        }
    }
    
    private int setValue(int value) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValue(value);
    }
    
    private int setValueByHeight(int value) {
        if (value == 0) {
            return 0;
        } else if (value == 1) {
            return 1;
        }
        return (int) calculateValueByHeight(value);
    }
    
    private float calculateValue(float value) {
        if ("px".equals(unit)) {
            return value * ((float) actualWidth / (float) designWidth);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return value * ((float) actualWidth / (float) designWidth);
            
        }
        return 0;
    }
    
    private float calculateValueByHeight(float value) {
        if ("px".equals(unit)) {
            return value * ((float) actualHeight / (float) designHeight);
        } else if ("dp".equals(unit)) {
            int dip = px2dip(actualDensity, value);
            value = ((float) designDpi / 160) * dip;
            return value * ((float) actualHeight / (float) designHeight);
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
    public int getScaledValueByHeight(int px) {
        return setValueByHeight(px);
    }
}
