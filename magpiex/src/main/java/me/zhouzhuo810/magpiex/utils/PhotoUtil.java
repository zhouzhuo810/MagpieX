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
 * 选择照片工具类
 *
 * @author zhouzhuo810
 * @date 6/15/21 2:24 PM
 */
public class PhotoUtil {
    
    public static final int REQUEST_CODE_CAMERA = 0x99;
    
    /**
     * 选择图片
     * 注意使用前先判断存储权限
     *
     * @param fileChooser FileChooser
     * @return 是否支持选择图片
     */
    public static boolean chooseImage(FileChooser fileChooser) {
        return chooseImage(fileChooser, false);
    }
    
    /**
     * 选择图片
     * 注意使用前先判断存储权限
     *
     * @param fileChooser FileChooser
     * @return 是否支持选择图片
     */
    
    public static boolean chooseImage(FileChooser fileChooser, boolean allowMultiple) {
        return fileChooser.showFileChooser("image/*", "选择图片", allowMultiple, true);
    }
    
    /**
     * 获取选图结果 - 单选模式
     * {@link Activity#onActivityResult(int, int, Intent)}中调用此方法获取选择的图片文件
     *
     * <br>
     * {@code
     * PhotoUtil.onActivityResultPhoto(fileChooser, requestCode, resultCode, data);
     * }
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent
     * @return 图片文件
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
     * 获取选图结果 - 多选模式
     * {@link Activity#onActivityResult(int, int, Intent)}中调用此方法获取选择的图片文件
     *
     * <br>
     * {@code
     * PhotoUtil.onActivityResultPhoto(fileChooser, requestCode, resultCode, data);
     * }
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent
     * @return 图片文件
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
    
    /**
     * 拍照，使用前注意申请拍照权限（如果照片存在公有目录，则还需要申请存储权限）
     *
     * @param activity  BaseActivity
     * @param dir       保存文件夹的路径
     * @param authority 7.0 FileProvider的authority（一般为包名+".fileProvider"）
     */
    public static void takePhoto(BaseActivity activity, File dir, String authority) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        String realPath = dir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
        SpUtil.putString(Cons.SP_KEY_OF_TAKE_PHOTO_CAMERA_PIC_PATH, realPath);
        final File targetFile = new File(realPath);
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(targetFile);
        } else {
            uri = FileProvider.getUriForFile(activity,
                authority,
                targetFile);
        }
        if (targetFile.exists()) {
            targetFile.delete();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }
    
    /**
     * 拍照结果
     * {@link Activity#onActivityResult(int, int, Intent)}中调用此方法获取选择的图片文件
     *
     * <br>
     * {@code
     * PhotoUtil.onActivityResultCamera(requestCode, resultCode, data);
     * }
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent
     * @return 图片文件
     */
    public static File onActivityResultCamera(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                String path = SpUtil.getString(Cons.SP_KEY_OF_TAKE_PHOTO_CAMERA_PIC_PATH);
                if (path != null) {
                    return new File(path);
                }
            }
        }
        return null;
    }
}
