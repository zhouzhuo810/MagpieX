package me.zhouzhuo810.magpiex.utils.loadviewhelper;

import android.view.View;

/**
 * Created by yatoooon on 2018/5/24.
 */

public interface ILoadViewHelper {
    
    void loadWidthHeightFont(View view, boolean forceWidthHeight);

    void loadPadding(View view, boolean forceWidthHeight);

    void loadLayoutMargin(View view, boolean forceWidthHeight);
    
    void loadMaxWidthAndHeight(View view, boolean forceWidthHeight);
    
    void loadMinWidthAndHeight(View view, boolean forceWidthHeight);

    /**
     * 动态计算数值大小(按宽度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValue(int px);
    
    /**
     * 动态计算数值大小(按高度缩放)
     *
     * @param px 原始数据
     * @return 缩放数据
     */
    int getScaledValueByHeight(int px);
}
