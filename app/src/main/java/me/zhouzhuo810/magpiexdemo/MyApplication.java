package me.zhouzhuo810.magpiexdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

import androidx.core.app.ActivityCompat;
import me.zhouzhuo810.magpiex.app.BaseApplication;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.CrashUtil;
import me.zhouzhuo810.magpiex.utils.NoticeUtil;

public class MyApplication extends BaseApplication {
    
    private static MyApplication instance;
    
    private static final String CRASH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator + "Magpie" + File.separator + "Log";
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        instance = this;
        
        NoticeUtil.initNoticeChannel("1", getString(R.string.app_name), "Magpie通知渠道", 0, true);
        
        //Crash Handler
        //        initCrash();
    }
    
    
    public static MyApplication getInstance() {
        return instance;
    }
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return true;
    }
    
    private void initCrash() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            CrashUtil.init(new CrashUtil.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {
                    BaseUtil.relaunchApp();
                }
            });
        } else {
            CrashUtil.init(CRASH_PATH, new CrashUtil.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {
                    BaseUtil.relaunchApp();
                }
            });
        }
    }
}
