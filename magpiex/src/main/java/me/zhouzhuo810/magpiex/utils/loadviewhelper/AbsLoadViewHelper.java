package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import me.zhouzhuo810.magpiex.utils.conversion.IConversion;
import me.zhouzhuo810.magpiex.utils.conversion.SimpleConversion;


/**
 * Created by yosemite on 2018/3/21.
 */

public abstract class AbsLoadViewHelper implements ILoadViewHelper {
    
    protected float actualDensity;
    protected float actualDensityDpi;
    protected float actualWidth;
    protected float actualHeight;
    
    protected boolean scaleWidthAndHeight;
    
    protected int designWidth;
    protected int designHeight;
    protected int designDpi;
    protected float fontSize;
    protected String unit;
    
    public AbsLoadViewHelper(Context context, boolean scaleWidthAndHeight, int designWidth, int designHeight, int designDpi, float fontSize, String unit) {
        this.scaleWidthAndHeight = scaleWidthAndHeight;
        this.designWidth = designWidth;
        this.designHeight = designHeight;
        this.designDpi = designDpi;
        this.fontSize = fontSize;
        this.unit = unit;
        setActualParams(context);
    }
    
    public void reset(Context context) {
        setActualParams(context);
    }
    
    private void setActualParams(Context context) {
        float[] actualScreenInfo = ActualScreen.screenInfo(context);
        if (actualScreenInfo != null && actualScreenInfo.length == 4) {
            actualWidth = actualScreenInfo[0];
            actualHeight = actualScreenInfo[1];
            actualDensity = actualScreenInfo[2];
            actualDensityDpi = actualScreenInfo[3];
        }
    }
    
    // if subclass has owner conversion，you need override this method and provide your conversion
    public void loadView(View view) {
        loadView(view, new SimpleConversion(), false);
    }
    
    public void loadView(View view, boolean forceWidthHeight) {
        loadView(view, new SimpleConversion(), forceWidthHeight);
    }
    
    public final void loadView(View view, IConversion conversion, boolean forceWidthHeight) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            conversion.transform(viewGroup, this, forceWidthHeight);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    loadView(viewGroup.getChildAt(i), conversion, forceWidthHeight);
                } else {
                    conversion.transform(viewGroup.getChildAt(i), this, forceWidthHeight);
                }
            }
        } else {
            conversion.transform(view, this, forceWidthHeight);
        }
        
    }
    
}
