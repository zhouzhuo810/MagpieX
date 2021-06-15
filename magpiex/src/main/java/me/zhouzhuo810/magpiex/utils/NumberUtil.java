package me.zhouzhuo810.magpiex.utils;

import java.text.DecimalFormat;

/**
 * 数字转换工具类
 *
 * @author zhouzhuo810
 * @date 6/15/21 2:21 PM
 */
public class NumberUtil {
    
    
    /**
     * 字符串转 float
     *
     * @param floatStr 字符串
     * @return float
     */
    public static float toFloat(String floatStr) {
        if (floatStr == null) {
            return 0f;
        }
        try {
            return Float.parseFloat(floatStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0f;
    }
    
    /**
     * 字符串转 double
     *
     * @param doubleStr 字符串
     * @return double
     */
    public static double toDouble(String doubleStr) {
        if (doubleStr == null) {
            return 0;
        }
        try {
            return Double.parseDouble(doubleStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 字符串转 int
     *
     * @param intStr 字符串
     * @return int
     */
    public static int toInteger(String intStr) {
        if (intStr == null) {
            return 0;
        }
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 字符串转 long
     *
     * @param longStr 字符串
     * @return long
     */
    public static long toLong(String longStr) {
        if (longStr == null) {
            return 0L;
        }
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    
    /**
     * 格式化浮点数为字符串
     *
     * @param f 浮点数
     * @param n 保留小数位
     * @return 字符串
     */
    public static String formatFloatWithDecimal(float f, int n) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#0.");
        if (n <= 1) {
            pattern.append("0");
        } else {
            for (int i = 0; i < n; i++) {
                pattern.append("0");
            }
        }
        return new DecimalFormat(pattern.toString()).format(f);
    }
    
}
