package me.zhouzhuo810.magpiex.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.File;

/**
 * 选择视频工具类
 *
 * @author zhouzhuo810
 * @date 7/6/21 3:50 PM
 */
public class VideoUtil {
    
    /**
     * 选择视频
     * 注意使用前先判断存储权限
     *
     * @param fileChooser FileChooser
     * @return 是否支持选择视频
     */
    public static boolean chooseVideo(FileChooser fileChooser) {
        return chooseVideo(fileChooser, false);
    }
    
    /**
     * 选择视频
     * 注意使用前先判断存储权限
     *
     * @param fileChooser FileChooser
     * @return 是否支持选择视频
     */
    public static boolean chooseVideo(FileChooser fileChooser, boolean allowMultiple) {
        return fileChooser.showFileChooser("video/*", "选择视频", allowMultiple, true);
    }
    
    /**
     * 获取选择结果 - 单选模式
     * {@link Activity#onActivityResult(int, int, Intent)}中调用此方法获取选择的视频文件
     *
     * <br>
     * {@code
     * PhotoUtil.onActivityResultPhoto(fileChooser, requestCode, resultCode, data);
     * }
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent
     * @return 视频文件
     */
    public static File onActivityResultPhoto(FileChooser fileChooser, final int requestCode, final int resultCode, final Intent data) {
        if (fileChooser == null) {
            return null;
        }
        boolean b = fileChooser.onActivityResult(requestCode, resultCode, data);
        if (b) {
            File[] chosenFiles = fileChooser.getChosenFiles();
            if (chosenFiles != null && chosenFiles.length > 0) {
                return chosenFiles[0];
            }
        }
        return null;
    }
    
    /**
     * 获取选择结果 - 多选模式
     * {@link Activity#onActivityResult(int, int, Intent)}中调用此方法获取选择的视频文件
     *
     * <br>
     * {@code
     * PhotoUtil.onActivityResultPhoto(fileChooser, requestCode, resultCode, data);
     * }
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent
     * @return 视频文件
     */
    public static File[] onActivityResultPhotoMulti(FileChooser fileChooser, final int requestCode, final int resultCode, final Intent data) {
        if (fileChooser == null) {
            return null;
        }
        boolean b = fileChooser.onActivityResult(requestCode, resultCode, data);
        if (b) {
            File[] chosenFiles = fileChooser.getChosenFiles();
            if (chosenFiles != null && chosenFiles.length > 0) {
                return chosenFiles;
            }
        }
        return null;
    }
    
}
