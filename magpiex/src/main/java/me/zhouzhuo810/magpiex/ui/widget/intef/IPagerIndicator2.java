package me.zhouzhuo810.magpiex.ui.widget.intef;


import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import me.zhouzhuo810.magpiex.ui.widget.Indicator2;

/**
 * @author zz
 * @date 2016/8/22
 */
public interface IPagerIndicator2 {
    
    /**
     * bind indicator to your viewpager.
     *
     * 如果重新{@link ViewPager2#setAdapter}，需要重新调用 {@link Indicator2#setViewPager2}
     *
     * @param viewPager2 your viewpager
     */
    Indicator2 setViewPager2(ViewPager2 viewPager2);
    
    /**
     * 跳转到目标页
     *
     * @param position 位置
     * @param animate  是否显示动画
     */
    Indicator2 setCurrentItem(int position, boolean animate);
    
    /**
     * 选中，只改变样式，不会调用{@link ViewPager#setCurrentItem(int)} 方法
     *
     * @param position 位置
     */
    Indicator2 select(int position);
    
    /**
     * 动态更新文字
     *
     * @param position 位置
     * @param title    标题
     */
    Indicator2 updateText(int position, String title);
    
    /**
     * 设置选中的文字的颜色
     *
     * @param tabTextColorSelect 颜色值
     */
    Indicator2 setTabTextColorSelect(int tabTextColorSelect);
    
    /**
     * 设置未选中的文字的颜色
     *
     * @param tabTextColorUnSelect 颜色值
     */
    Indicator2 setTabTextColorUnSelect(int tabTextColorUnSelect);
    
    /**
     * 设置下划线的颜色
     *
     * @param underlineColor 颜色值
     */
    Indicator2 setUnderlineColor(int underlineColor);
    
    /**
     * 设置文字和图标的方向
     *
     * @param orientation 方向
     */
    Indicator2 setTabTextIconOrientation(Indicator2.TabOrientation orientation);
    
    /**
     * 刷新
     */
    Indicator2 update();
}
