package me.zhouzhuo810.magpiex.utils;

public class ThreadUtil {
    
    /**
     * 获取当前类的类名，带后缀
     *
     * @param clazzName 类名，不带后缀
     * @return 类名
     */
    public static String getClazzFileName(String clazzName) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            String fileName = stackTraceElement.getFileName();
            if (fileName.contains(clazzName)) {
                return fileName;
            }
        }
        return null;
    }
}
