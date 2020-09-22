package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.view.View;

/**
 * Created by yatoooon on 2018/5/24.
 */

public interface ILoadViewHelper {
    
    void loadWidthHeightFont(View view);
    
    void loadPadding(View view);
    
    void loadLayoutMargin(View view);
    
    void loadMaxWidthAndHeight(View view);
    
    void loadMinWidthAndHeight(View view);
    
    /**
     * 动态计算数值大小(根据screen_adapt_type判断，auto或者width则按width，否则按height)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValue(int px);
    
    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByWidth(int px);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByHeight(int px);
}
