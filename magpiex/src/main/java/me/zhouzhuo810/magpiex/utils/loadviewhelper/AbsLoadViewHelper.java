package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.conversion.IConversion;
import me.zhouzhuo810.magpiex.utils.conversion.SimpleConversion;


/**
 * 屏幕适配工具抽象类
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:43 PM
 */
public abstract class AbsLoadViewHelper implements ILoadViewHelper {
    
    /**
     * 是否禁用屏幕适配，避免频繁调用 BaseUtil.isScreenAdaptEnable 方法。
     */
    protected boolean disableAdapt;
    protected int screenOrientation;
    protected float actualDensity;
    protected float actualDensityDpi;
    protected float actualWidth;
    protected float actualHeight;
    
    protected String adaptType;
    
    protected int designWidth;
    protected int designHeight;
    protected int designDpi;
    protected float fontSize;
    protected String unit;
    
    public AbsLoadViewHelper(Context context, String adaptType, int designWidth, int designHeight, int designDpi, float fontSize, String unit) {
        this.adaptType = adaptType;
        this.designWidth = designWidth;
        this.designHeight = designHeight;
        this.designDpi = designDpi;
        this.fontSize = fontSize;
        this.unit = unit;
        this.disableAdapt = !BaseUtil.isScreenAdaptEnable();
        setActualParams(context);
    }
    
    public void reset(Context context) {
        setActualParams(context);
    }
    
    private void setActualParams(Context context) {
        float[] actualScreenInfo = ActualScreen.screenInfo(context);
        if (actualScreenInfo != null && actualScreenInfo.length == 5) {
            screenOrientation = (int) actualScreenInfo[0];
            actualWidth = actualScreenInfo[1];
            actualHeight = actualScreenInfo[2];
            actualDensity = actualScreenInfo[3];
            actualDensityDpi = actualScreenInfo[4];
        }
        this.disableAdapt = !BaseUtil.isScreenAdaptEnable();
    }
    
    /**
     * 不建议使用此方法，推荐使用{@link me.zhouzhuo810.magpiex.utils.SimpleUtil#scaleView(View)}方法。
     *
     * @param view 要缩放的View
     */
    @Deprecated
    public void loadView(View view) {
        if (disableAdapt) {
            return;
        }
        loadView(view, new SimpleConversion());
    }
    
    public final void loadView(View view, IConversion conversion) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            conversion.transform(viewGroup, this);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    loadView(viewGroup.getChildAt(i), conversion);
                } else {
                    conversion.transform(viewGroup.getChildAt(i), this);
                }
            }
        } else {
            conversion.transform(view, this);
        }
        
    }
    
}
