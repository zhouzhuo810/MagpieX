package me.zhouzhuo810.magpiex.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Gravity;
import android.webkit.WebView;

import com.hjq.toast.ToastUtils;

import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;

/**
 * 使用此框架，强烈建议自定义的Application继承BaseApplication，否则需要自行复制内部逻辑。
 *
 * @author zhouzhuo810
 */
public abstract class BaseApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        BaseUtil.init(this);
        
        //是否启用屏幕适配
        BaseUtil.setScreenAdaptEnable(!isScreenAdaptDisable());
        
        //是否打印日志
        BaseUtil.setLogEnable(isDebugEnable());
        
        //顺便初始化屏幕适配工具类
        ScreenAdapterUtil.init(this);
        
        LanguageUtil.init(this);
        
        ToastUtils.init(this);
        
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, SimpleUtil.getScaledValue(200));
        
        try {
            configWebViewProcessSuffix();
        } catch (RuntimeException ignored) {
        }
        
        if (shouldSupportMultiLanguage()) {
            try {
                new WebView(this, null).destroy();
            } catch (Exception ignored) {
            }
            LanguageUtil.updateApplicationLanguage();
        }
    }
    
    private void configWebViewProcessSuffix() throws RuntimeException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(this);
            String packageName = getPackageName();
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }
    
    private String getProcessName(Context context) {
        if (context == null) {
            return null;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }
    
    
    @Override
    protected void attachBaseContext(Context base) {
        if (shouldSupportMultiLanguage()) {
            int language = SpUtil.getInt(base, Cons.SP_KEY_OF_CHOOSED_LANGUAGE, -1);
            super.attachBaseContext(LanguageUtil.attachBaseContext(base, language, getSupportLanguages()));
        } else {
            super.attachBaseContext(base);
        }
    }
    
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (shouldSupportMultiLanguage()) {
            LanguageUtil.updateApplicationLanguage();
        }
        SimpleUtil.resetScale(this);
    }
    
    /**
     * 是否支持多语言
     *
     * @return true/false, 默认返回false
     * <p>
     * 如果返回false，表示您的app不需要支持多语言；
     * <p>
     * 如果返回true，表示您的app需要支持多语言
     * <p>
     * 可以使用{@link Application#onCreate()}中调用
     * {@link LanguageUtil#setGlobalLanguage(Integer)}
     * 方法设置默认语言
     * <p>
     */
    public abstract boolean shouldSupportMultiLanguage();
    
    /**
     * 获取Code对应的语言
     *
     * @return 自定义Code
     */
    public abstract Map<Integer, Locale> getSupportLanguages();
    
    /**
     * 是否关闭屏幕适配
     *
     * @return 是否
     */
    public abstract boolean isScreenAdaptDisable();
    
    /**
     * 是否打印日志
     *
     * @return 是否
     */
    public abstract boolean isDebugEnable();
}
