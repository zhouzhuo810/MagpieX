package me.zhouzhuo810.magpiexdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import me.zhouzhuo810.magpiex.app.BaseApplication;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.CrashUtil;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;
import me.zhouzhuo810.magpiex.utils.NoticeUtil;
import me.zhouzhuo810.magpiexdemo.constants.MyCons;

public class MyApplication extends BaseApplication {
    
    private static MyApplication instance;
    
    private static final String CRASH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator + "Magpie" + File.separator + "Log";
    
    public static Map<Integer, Locale> mSupportLanguages = new HashMap<Integer, Locale>(4) {{
        put(MyCons.LANGUAGE_CH_SIMPLE, Locale.SIMPLIFIED_CHINESE);
        put(MyCons.LANGUAGE_CH_COMPLEX, Locale.TRADITIONAL_CHINESE);
        put(MyCons.LANGUAGE_EN, Locale.ENGLISH);
        put(MyCons.LANGUAGE_VI, new Locale("vi"));
    }};
    
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
    
    @Override
    public Map<Integer, Locale> getSupportLanguages() {
//        if (mMap == null) {
//            mMap = new HashMap<>();
//            mMap.put(MyCons.LANGUAGE_CH_SIMPLE, Locale.SIMPLIFIED_CHINESE);
//            mMap.put(MyCons.LANGUAGE_CH_COMPLEX, Locale.TRADITIONAL_CHINESE);
//            mMap.put(MyCons.LANGUAGE_EN, Locale.ENGLISH);
//            mMap.put(MyCons.LANGUAGE_VI, new Locale("vi"));
//        }
//        return mMap;
        return mSupportLanguages;
    }
    
    @Override
    public boolean isScreenAdaptDisable() {
        return false;
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
