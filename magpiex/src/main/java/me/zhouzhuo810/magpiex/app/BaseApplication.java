package me.zhouzhuo810.magpiex.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;

import com.hjq.toast.ToastUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;

public abstract class BaseApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        BaseUtil.init(this);
        
        LanguageUtil.init(this);
        
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, SimpleUtil.getScaledValue(200));
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        if (shouldSupportMultiLanguage()) {
            int language = SpUtil.getInt(base, Cons.SP_KEY_OF_CHOOSED_LANGUAGE);
            super.attachBaseContext(LanguageUtil.attachBaseContext(base, language));
        } else {
            super.attachBaseContext(base);
        }
        
        BaseUtil.init(base, true);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
     * {@link LanguageUtil#setGlobalLanguage(int)}
     * 方法设置默认语言
     * <p>
     */
    public abstract boolean shouldSupportMultiLanguage();
    
    /**
     * 获取Code对应的语言
     * @return 自定义Code
     */
    public abstract Map<Integer, Locale> getSupportLanguages();
}
