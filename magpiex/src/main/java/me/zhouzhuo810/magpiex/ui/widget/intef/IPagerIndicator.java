package me.zhouzhuo810.magpiex.ui.widget.intef;


import androidx.viewpager.widget.ViewPager;
import me.zhouzhuo810.magpiex.ui.widget.Indicator;

/**
 * @author zz
 * @date 2016/8/22
 */
public interface IPagerIndicator {
    
    /**
     * bind indicator to your viewpager.
     *
     * 如果重新{@link ViewPager#setAdapter}，需要重新调用 {@link Indicator#setViewPager}
     *
     * @param viewPager your viewpager
     */
    Indicator setViewPager(ViewPager viewPager);
    
    /**
     * 跳转到目标页
     *
     * @param position 位置
     * @param animate  是否显示动画
     */
    Indicator setCurrentItem(int position, boolean animate);
    
    /**
     * 选中，只改变样式，不会调用{@link ViewPager#setCurrentItem(int)} 方法
     *
     * @param position 位置
     */
    Indicator select(int position);
    
    /**
     * 动态更新文字
     *
     * @param position 位置
     * @param title    标题
     */
    Indicator updateText(int position, String title);
    
    /**
     * 设置选中的文字的颜色
     *
     * @param tabTextColorSelect 颜色值
     */
    Indicator setTabTextColorSelect(int tabTextColorSelect);
    
    /**
     * 设置未选中的文字的颜色
     *
     * @param tabTextColorUnSelect 颜色值
     */
    Indicator setTabTextColorUnSelect(int tabTextColorUnSelect);
    
    /**
     * 设置下划线的颜色
     *
     * @param underlineColor 颜色值
     */
    Indicator setUnderlineColor(int underlineColor);
    
    /**
     * 设置文字和图标的方向
     *
     * @param orientation 方向
     */
    Indicator setTabTextIconOrientation(Indicator.TabOrientation orientation);
    
    /**
     * 刷新
     */
    Indicator update();
}
