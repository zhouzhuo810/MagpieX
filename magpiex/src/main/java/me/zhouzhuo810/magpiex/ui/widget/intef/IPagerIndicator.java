package me.zhouzhuo810.magpiex.ui.widget.intef;


import androidx.viewpager.widget.ViewPager;

/**
 *
 * @author zz
 * @date 2016/8/22
 */
public interface IPagerIndicator {

    /**
     * 绑定ViewPager
     *
     * @param viewPager viewPager
     */
    void setViewPager(ViewPager viewPager);

    /**
     * 跳转到目标页
     *
     * @param position 位置
     * @param animate  是否显示动画
     */
    void setCurrentItem(int position, boolean animate);
    
    
    /**
     * 动态更新文字
     *
     * @param position 位置
     * @param title    标题
     */
    void updateText(int position, String title);
}
