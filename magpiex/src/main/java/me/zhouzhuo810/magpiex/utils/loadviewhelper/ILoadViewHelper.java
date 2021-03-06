package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.view.View;

/**
 * Created by yatoooon on 2018/5/24.
 */

public interface ILoadViewHelper {
    
    /**
     * 缩放width，height，textSize
     *
     * @param view View
     */
    void loadWidthHeightFont(View view);
    
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
    int getScaledValue(int px);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValue(float px);
    
    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByWidth(int px);
    
    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValueByWidth(float px);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByHeight(int px);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    float getScaledValueByHeight(float px);
}
