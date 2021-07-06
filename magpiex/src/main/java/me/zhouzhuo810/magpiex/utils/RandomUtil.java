package me.zhouzhuo810.magpiex.utils;

import android.graphics.Color;

/**
 * 随机数生成工具
 *
 * @author zhouzhuo810
 * @date 7/6/21 3:53 PM
 */
public class RandomUtil {
    
    /**
     * 获取随机从[from,to)的随机整数
     */
    public static int getRandomIntFromTo(int from, int to) {
        return (int) (Math.random() * (to - from)) + from;
    }
    
    /**
     * 获取随机从[from,to)的随机浮点数double
     */
    public static double getRandomDoubleFromTo(int from, int to) {
        return (Math.random() * (to - from)) + from;
    }
    
    /**
     * 获取随机从[from,to)的随机浮点数float
     */
    public static float getRandomFloatFromTo(int from, int to) {
        return (float) ((Math.random() * (to - from)) + from);
    }
    
    /**
     * 获取随机6位数
     */
    public static int getRandomSixInt() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
    
    /**
     * 获取随机4位数
     */
    public static int getRandomFourInt() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
    
    
    /**
     * 获取区间为(黑色,白色)的随机颜色
     *
     * @return 随机颜色
     */
    public static int getRandomColorBlackToWhite() {
        return getRandomColorFromTo(Color.BLACK, Color.WHITE);
    }
    
    /**
     * 获取区间为(from,to)的随机颜色
     *
     * @param from 开始颜色
     * @param to   结束颜色
     * @return 随机颜色
     */
    public static int getRandomColorFromTo(int from, int to) {
        return ColorUtil.computeColor(from, to, (float) Math.random());
    }
}
