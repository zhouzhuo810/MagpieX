package me.zhouzhuo810.magpiex.utils;

import android.widget.Toast;

import com.hjq.toast.ToastUtils;

/**
 * 吐司工具类
 */
public class ToastUtil {
    
    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 弹出吐司
     *
     * @param msg 消息内容
     */
    public static void showToast(CharSequence msg) {
        ToastUtils.show(msg);
    }
    
    /**
     * 弹出吐司
     *
     * @param msg 消息内容
     * @deprecated 没有长短之分了
     */
    public static void showShortToast(CharSequence msg) {
        ToastUtils.show(msg);
    }
    
    /**
     * 弹出吐司
     *
     * @param msg 消息内容
     * @deprecated 没有长短之分了
     */
    public static void showLongToast(CharSequence msg) {
        ToastUtils.show(msg);
    }
    
    /**
     * 弹出短吐司
     *
     * @param msg 消息内容
     */
    public static void showSystemShortToast(CharSequence msg) {
        Toast.makeText(BaseUtil.getApp(), msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 弹出长吐司
     *
     * @param msg 消息内容
     */
    public static void showSystemLongToast(CharSequence msg) {
        Toast.makeText(BaseUtil.getApp(), msg, Toast.LENGTH_LONG).show();
    }
    
}
