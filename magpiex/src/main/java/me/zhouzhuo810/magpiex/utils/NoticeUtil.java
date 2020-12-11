package me.zhouzhuo810.magpiex.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.ui.act.CopyUrlActivity;

public class NoticeUtil {
    private NoticeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    public static final int NOTICE_NORMAL = 0x11;
    public static final int NOTICE_PROGRESS = 0x12;
    public static int NOTICE_URL = 0;
    public static final String DEFAULT_CHANNEL_ID = "default";
    
    /**
     * 显示普通的通知
     *
     * @param title      标题
     * @param content    内容
     * @param autoCancel 是否自动取消
     * @param onGoing    是否常驻
     * @param smallIcon  小图标
     * @param voice      是否开启声音
     * @param vibrate    是否振动
     * @param channelId  分类id
     * @param intent     点击的intent
     */
    public static void showNormalNotice(String title,
                                        String content, boolean autoCancel,
                                        boolean onGoing, int smallIcon,
                                        boolean voice, boolean vibrate,
                                        String channelId,
                                        Intent intent) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(BaseUtil.getApp(), channelId == null ? DEFAULT_CHANNEL_ID : channelId);
        b.setContentTitle(title)
            .setContentText(content)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(autoCancel)
            .setOngoing(onGoing)
            .setSmallIcon(smallIcon)
            .setDefaults(voice ? Notification.DEFAULT_SOUND : Notification.DEFAULT_ALL)
            .setVibrate(vibrate ? new long[]{200, 200} : null);
        if (intent != null) {
            b.setContentIntent(PendingIntent.getActivity(BaseUtil.getApp(), RandomUtil.getRandomSixInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        NotificationManagerCompat nm = NotificationManagerCompat.from(BaseUtil.getApp());
        nm.notify(NOTICE_NORMAL, b.build());
    }
    
    /**
     * 显示普通的自定义声音的通知
     *
     * @param title      标题
     * @param content    内容
     * @param autoCancel 是否自动取消
     * @param onGoing    是否常驻
     * @param smallIcon  小图标
     * @param voice      是否开启声音
     * @param voiceId    声音资源id , R.raw.xxx
     * @param vibrate    是否振动
     * @param channelId  分类id
     * @param intent     点击的intent
     */
    public static void showNormalNoticeWithCustomVoice(String title,
                                                       String content, boolean autoCancel,
                                                       boolean onGoing, int smallIcon,
                                                       boolean voice, int voiceId, boolean vibrate, String channelId,
                                                       Intent intent) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(BaseUtil.getApp(), channelId == null ? DEFAULT_CHANNEL_ID : channelId);
        b.setContentTitle(title)
            .setContentText(content)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(autoCancel)
            .setOngoing(onGoing)
            .setSmallIcon(smallIcon)
            .setVibrate(vibrate ? new long[]{200, 200} : null);
        if (voice) {
            b.setSound(Uri.parse("android.resource://" + BaseUtil.getPackageInfo(BaseUtil.getApp()).packageName + "/" + voiceId));
        }
        if (intent != null) {
            PendingIntent pi = PendingIntent.getActivity(BaseUtil.getApp(), RandomUtil.getRandomSixInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            b.setContentIntent(pi);
        }
        Notification notification = b.build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(BaseUtil.getApp());
        nm.notify(NOTICE_NORMAL, notification);
    }
    
    /**
     * 隐藏普通的通知
     */
    public static void hideNormalNotice(Context context) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.cancel(NOTICE_NORMAL);
    }
    
    /**
     * @param title      标题
     * @param progress   进度
     * @param smallIcon  小图标
     * @param autoCancel 自动取消
     * @param onGoing    是否常驻
     * @param channelId  分类id
     * @param intent     点击的intent
     */
    public static void showProgressNotice(String title, int progress, int smallIcon, boolean autoCancel,
                                          boolean onGoing, String channelId, Intent intent) {
        
        NotificationCompat.Builder b = new NotificationCompat.Builder(BaseUtil.getApp(), channelId == null ? DEFAULT_CHANNEL_ID : channelId);
        b.setContentTitle(title)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(autoCancel)
            .setProgress(100, progress, false)
            .setOngoing(onGoing)
            .setSmallIcon(smallIcon)
            .setVibrate(null)
            .setSound(null);
        if (intent != null) {
            PendingIntent pi = PendingIntent.getActivity(BaseUtil.getApp(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            b.setContentIntent(pi);
        }
        NotificationManagerCompat nm = NotificationManagerCompat.from(BaseUtil.getApp());
        nm.notify(NOTICE_PROGRESS, b.build());
    }
    
    /**
     * 关闭进度通知栏
     */
    public static void hideProgressNotice() {
        NotificationManagerCompat nm = NotificationManagerCompat.from(BaseUtil.getApp());
        nm.cancel(NOTICE_PROGRESS);
    }
    
    
    /**
     * 显示复制URL调试通知
     *
     * @param context     上下文
     * @param title       标题
     * @param showContent 通知内容
     * @param realContent 点击通知实际复制内容
     * @param autoCancel  是否自动取消
     * @param onGoing     是否常驻
     * @param smallIcon   小图标
     * @param vibrate     是否震动
     * @param channelId   通知渠道id
     */
    public static void showNormalNoticeWithCopyAction(Context context, String title,
                                                      String showContent, String realContent, boolean autoCancel,
                                                      boolean onGoing, int smallIcon, boolean vibrate, String channelId,
                                                      String targetAppPackageName) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, channelId == null ? DEFAULT_CHANNEL_ID : channelId);
        b.setContentTitle(title)
            .setContentText(showContent)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(autoCancel)
            .setOngoing(onGoing)
            .setSmallIcon(smallIcon)
            .setVibrate(vibrate ? new long[]{200, 200} : null);
        Intent intent = new Intent(context, CopyUrlActivity.class);
        intent.putExtra(Cons.NOTICE_ACTION, CopyUrlActivity.NOTICE_ACTION_COPY);
        intent.putExtra(Cons.NOTICE_URL, realContent);
        intent.putExtra(Cons.NOTICE_TARGET_APP_PACKAGE_NAME, targetAppPackageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, RandomUtil.getRandomSixInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pi);
        Notification notification = b.build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        final int id = (NOTICE_URL++) % 4 + 1;
        nm.notify(id, notification);
    }
    
    
    /**
     * 显示复制URL调试通知
     *
     * @param context     上下文
     * @param title       标题
     * @param showContent 通知内容
     * @param realContent 点击通知实际复制内容
     * @param autoCancel  是否自动取消
     * @param onGoing     是否常驻
     * @param smallIcon   小图标
     * @param vibrate     是否震动
     * @param channelId   通知渠道id
     */
    public static void showNormalNoticeWithShareAction(Context context, String title,
                                                       String showContent, String realContent, boolean autoCancel,
                                                       boolean onGoing, int smallIcon, boolean vibrate, String channelId) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, channelId == null ? DEFAULT_CHANNEL_ID : channelId);
        b.setContentTitle(title)
            .setContentText(showContent)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(autoCancel)
            .setOngoing(onGoing)
            .setSmallIcon(smallIcon)
            .setVibrate(vibrate ? new long[]{200, 200} : null);
        Intent intent = new Intent(context, CopyUrlActivity.class);
        intent.putExtra(Cons.NOTICE_ACTION, CopyUrlActivity.NOTICE_ACTION_SHARE);
        intent.putExtra(Cons.NOTICE_URL, realContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, RandomUtil.getRandomSixInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pi);
        Notification notification = b.build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        final int id = (NOTICE_URL++) % 4 + 1;
        nm.notify(id, notification);
    }
    
    
    /**
     * 初始化Android 8.0 通知渠道
     *
     * @param channelId   渠道id
     * @param channelName 渠道名称
     * @param channelDesc 渠道描述
     * @param soundRawId  声音id(R.raw.xxx),use 0 if not necessary
     */
    public static void initNoticeChannel(String channelId, String channelName, String channelDesc, int soundRawId, boolean vibration) {
        //8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) BaseUtil.getApp().getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // 配置通知渠道的属性
            mChannel.setDescription(channelDesc);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            if (soundRawId != 0) {
                mChannel.setSound(Uri.parse("android.resource://" + BaseUtil.getPackageInfo(BaseUtil.getApp()).packageName + "/" + soundRawId), null);
            }
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(vibration);
            if (vibration) {
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            }
            //最后在notificationmanager中创建该通知渠道
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }
    }
    
    /**
     * 通知权限是否打开
     *
     * @param context Context
     * @return 是否打开
     */
    public static boolean isNoticePermissionEnable(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }
    
    /**
     * 打开通知权限设置界面
     *
     * @param context Context
     */
    public static void launchNoticeSettings(Context context) {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(localIntent);
        } catch (Exception e) {
            localIntent = new Intent();
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(localIntent);
        }
    }
    
}
