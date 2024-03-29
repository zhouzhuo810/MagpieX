package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.view.View;

/**
 * 屏幕适配工具接口
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:44 PM
 */
public interface ILoadViewHelper {
    
    /**
     * 缩放width，height
     *
     * @param view View
     */
    void loadWidthHeight(View view);
    
    /**
     * 缩放textSize
     *
     * @param view
     */
    void loadFontSize(View view);
    
    /**
     * 缩放padding
     *
     * @param view View
     */
    void loadPadding(View view);
    
    /**
     * 缩放margin
     *
     * @param view View
     */
    void loadLayoutMargin(View view);
    
    /**
     * 缩放最大宽高
     *
     * @param view View
     */
    void loadMaxWidthAndHeight(View view);
    
    /**
     * 缩放最小宽高
     *
     * @param view View
     */
    void loadMinWidthAndHeight(View view);
    
    /**
     * 缩放自定义属性
     *
     * @param view View
     */
    void loadCustomAttrs(View view);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValue(int px, boolean isFontSize);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValue(float px, boolean isFontSize);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px         原始数据
     * @param horizontal 是否水平方向
     * @return 缩放数据
     */
    int getScaledValue(int px, boolean horizontal, boolean isFontSize);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px         原始数据
     * @param horizontal 是否水平方向
     * @return 缩放数据
     */
    float getScaledValue(float px, boolean horizontal, boolean isFontSize);
    
    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByWidth(int px, boolean isFontSize);
    
    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValueByWidth(float px, boolean isFontSize);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByHeight(int px, boolean isFontSize);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValueByHeight(float px, boolean isFontSize);
}
