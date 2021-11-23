package me.zhouzhuo810.magpiex.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 字符串工具类
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:59 PM
 */
public class StrUtil {
    
    /**
     * 是否为空或长度为0
     *
     * @param text string
     * @return 是／否
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }
    
    /**
     * 是否为空
     *
     * @param text string
     * @return 是/否
     */
    public static boolean isNull(String text) {
        return text == null;
    }
    
    /**
     * 是否长度为0
     *
     * @param text string
     * @return 是/否
     */
    public static boolean isSpace(@NonNull String text) {
        return text.equals("");
    }
    
    /**
     * 比较方法（短的在前，长的在后，相同长度的比较ASCII码）
     * <p>
     * 用于这个方法的第二参数 {@link Collections#sort(List, Comparator)}
     * <p>
     * 也可以直接使用我的这个 {@link CollectionUtil#sort(List)} 方法
     *
     * @param str1 String1
     * @param str2 String2
     * @return 返回值
     */
    public static int compare(final String str1, String str2) {
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        if (str1.length() > str2.length()) {
            return 1;
        } else if (str1.length() == str2.length()) {
            return str1.compareTo(str2);
        } else {
            return -1;
        }
    }
    
    /**
     * 分割字符串
     * @param content 字符串
     * @param split 分割符
     * @return 分割后的字符串数组，可能为null
     */
    @Nullable
    public static String[] split(String content, String split) {
        if (content == null || split == null) {
            return null;
        }
        return content.split(split);
    }
    
    
}
