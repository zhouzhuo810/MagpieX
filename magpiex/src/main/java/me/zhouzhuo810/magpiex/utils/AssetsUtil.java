package me.zhouzhuo810.magpiex.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * assets工具
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:52 PM
 */
public class AssetsUtil {

    /**
     * 获取assets文件内容
     *
     * @param path 文件路径
     * @return 内容
     */
    public static String getFileToStringFromAssets(String path) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = BaseUtil.getApp().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(path)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    
    /**
     * 获取assets文件的字节内容
     *
     * @param path 文件路径
     * @return 内容
     */
    public static byte[] getFileToByteArrayFromAssets(String path) {
        try {
            //获取assets资源管理器
            AssetManager assetManager = BaseUtil.getApp().getAssets();
            InputStream is = assetManager.open(path);
            int size = is.available();
            byte[] bytes = new byte[size];
            int read = is.read(bytes);
            is.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
