package me.zhouzhuo810.magpiex.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class ShareUtil {
    
    /**
     * 分享文件到微信 - File方式
     *
     * @param context   Context
     * @param authority FileProvider
     * @param file      要分享的文件
     */
    public static void shareFileToWeChat(Context context, String title, String authority, @Nullable String noInstallHintString, @NonNull File file) throws Exception {
        shareFileToWeChat(context, title, authority, "image/*", noInstallHintString, file);
    }
    
    
    /**
     * 分享文件到微信 - File方式
     *
     * @param context   Context
     * @param authority FileProvider
     * @param file      要分享的文件
     */
    public static void shareFileToWeChat(Context context, String title, String authority, String mimeType, @Nullable String noInstallHintString, @NonNull File file) throws Exception {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mm")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装微信无法分享到微信" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(BaseUtil.getApp(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);
    }
    
    
    /**
     * 分享文件到微信 - Uri方式
     *
     * @param context Context
     * @param uri     要分享的Uri
     */
    public static void shareFileToWeChat(Context context, String title, @Nullable String noInstallHintString, @NonNull Uri uri) throws Exception {
        shareFileToWeChat(context, title, "image/*", noInstallHintString, uri);
    }
    
    
    /**
     * 分享文件到微信 - Uri方式
     *
     * @param context Context
     * @param uri     要分享的Uri
     */
    public static void shareFileToWeChat(Context context, String title, String mimeType, @Nullable String noInstallHintString, @NonNull Uri uri) throws Exception {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mm")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装微信无法分享到微信" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);
    }
    
    /**
     * 分享文件到其他
     *
     * @param context   Context
     * @param authority FileProvider
     * @param file      要分享的文件
     */
    public static void shareFileToOther(Context context, String title, String authority, @NonNull File file) throws Exception {
        shareFileToOther(context, title, "image/*", authority, file);
    }
    
    
    /**
     * 分享文件到其他
     *
     * @param context   Context
     * @param authority FileProvider
     * @param file      要分享的文件
     */
    public static void shareFileToOther(Context context, String title, String mimeType, String authority, @NonNull File file) throws Exception {
        Intent intent = new Intent();
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(BaseUtil.getApp(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);
    }
    
    /**
     * 分享文件到其他
     *
     * @param context Context
     * @param uri     要分享的Uri
     */
    public static void shareFileToOther(Context context, String title, @NonNull Uri uri) throws Exception {
        shareFileToOther(context, title, "image/*", uri);
    }
    
    
    /**
     * 分享文件到其他
     *
     * @param context Context
     * @param uri     要分享的Uri
     */
    public static void shareFileToOther(Context context, String title, String mimeType, @NonNull Uri uri) throws Exception {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);
    }
    
    /**
     * 分享文件到QQ
     *
     * @param context             Context
     * @param authority           FileProvider
     * @param noInstallHintString 未安装TIM的提示文字
     * @param file                要分享的文件
     */
    public static void shareFileToQQ(Context context, String authority, @Nullable String noInstallHintString, @NonNull File file) throws Exception {
        shareFileToQQ(context, authority, "image/*", noInstallHintString, file);
    }
    
    /**
     * 分享文件到QQ
     *
     * @param context             Context
     * @param authority           FileProvider
     * @param noInstallHintString 未安装TIM的提示文字
     * @param file                要分享的文件
     */
    public static void shareFileToQQ(Context context, String authority, String mimeType, @Nullable String noInstallHintString, @NonNull File file) throws Exception {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mobileqq")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装QQ无法分享到QQ" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(BaseUtil.getApp(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share.setType(mimeType);
        context.startActivity(Intent.createChooser(share, "发送"));
    }
    
    
    /**
     * 分享文件到QQ
     *
     * @param context             Context
     * @param noInstallHintString 未安装TIM的提示文字
     * @param uri                 要分享的Uri
     */
    public static void shareFileToQQ(Context context, @Nullable String noInstallHintString, @NonNull Uri uri) throws Exception {
        shareFileToQQ(context, "image/*", noInstallHintString, uri);
    }
    
    /**
     * 分享文件到QQ
     *
     * @param context             Context
     * @param noInstallHintString 未安装TIM的提示文字
     * @param uri                 要分享的Uri
     */
    public static void shareFileToQQ(Context context, String mimeType, @Nullable String noInstallHintString, @NonNull Uri uri) throws Exception {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mobileqq")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装QQ无法分享到QQ" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share.setType(mimeType);
        context.startActivity(Intent.createChooser(share, "发送"));
    }
    
    /**
     * 分享文件到TIM
     *
     * @param context             Context
     * @param authority           FileProvider
     * @param noInstallHintString 未安装TIM的提示文字
     * @param file                要分享的文件
     */
    public static void shareFileToTIM(Context context, String authority, @Nullable String noInstallHintString, @NonNull File file) throws Exception {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.tim")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装TIM无法分享到TIM" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.tim", "com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(BaseUtil.getApp(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share.setType("image/*");
        context.startActivity(Intent.createChooser(share, "发送"));
    }
    
    /**
     * 分享文件到TIM
     *
     * @param context             Context
     * @param noInstallHintString 未安装TIM的提示文字
     * @param uri                 要分享的Uri
     */
    public static void shareFileToTIM(Context context, @Nullable String noInstallHintString, @NonNull Uri uri) throws Exception {
        if (uri == null) {
            return;
        }
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.tim")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装TIM无法分享到TIM" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.tim", "com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share.setType("image/*");
        context.startActivity(Intent.createChooser(share, "发送"));
    }
    
    /**
     * 分享文字给QQ好友
     */
    public static void shareTextToQQ(Context context, String content, @Nullable String noInstallHintString, @Nullable String chooserTitle) {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mobileqq")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装QQ无法分享到QQ" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("text/plain");
        try {
            sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            Intent chooserIntent = Intent.createChooser(sendIntent, chooserTitle == null ? "发送" : chooserTitle);
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(chooserIntent);
        } catch (Exception e) {
            context.startActivity(sendIntent);
        }
    }
    
    /**
     * 分享文字给TIM好友
     */
    public static void shareTextToTIM(Context context, String content, @Nullable String noInstallHintString, @Nullable String chooserTitle) {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.tim")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装TIM无法分享到TIM" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("text/plain");
        try {
            sendIntent.setClassName("com.tencent.tim", "com.tencent.mobileqq.activity.JumpActivity");
            Intent chooserIntent = Intent.createChooser(sendIntent, chooserTitle == null ? "选择分享途径" : chooserTitle);
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(chooserIntent);
        } catch (Exception e) {
            context.startActivity(sendIntent);
        }
    }
    
    /**
     * 分享文字给微信好友
     */
    public static void shareTextToWeChat(Context context, String content, @Nullable String noInstallHintString, @Nullable String chooserTitle) {
        if (PackageUtil.isAppNotInstalled(BaseUtil.getApp(), "com.tencent.mm")) {
            Toast.makeText(BaseUtil.getApp(), noInstallHintString == null ? "未安装微信无法分享到微信" : noInstallHintString, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        try {
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            Intent chooserIntent = Intent.createChooser(intent, chooserTitle == null ? "选择分享途径" : chooserTitle);
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(chooserIntent);
        } catch (Exception e) {
            context.startActivity(intent);
        }
    }
    
    /**
     * 分享文字给其他
     */
    public static void shareTextToOther(Context context, String content, @Nullable String chooserTitle) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            Intent chooserIntent = Intent.createChooser(intent, chooserTitle == null ? "选择分享途径" : chooserTitle);
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(chooserIntent);
        } catch (Exception e) {
            context.startActivity(intent);
        }
    }
    
}
