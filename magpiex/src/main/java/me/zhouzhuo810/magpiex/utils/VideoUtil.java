package me.zhouzhuo810.magpiex.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

import androidx.core.content.FileProvider;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;

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
        return fileChooser.showFileChooser("video/*", "选择视频", false, true);
    }
    
    /**
     * 获取选择结果
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
    
}
