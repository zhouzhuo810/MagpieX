package me.zhouzhuo810.magpiex.utils;

import java.text.DecimalFormat;

public class NumberUtil {
    
    
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
