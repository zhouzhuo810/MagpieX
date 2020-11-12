package me.zhouzhuo810.magpiex.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;
import java.util.Map;

import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.app.BaseApplication;
import me.zhouzhuo810.magpiex.cons.Cons;

/**
 * 多语言工具类
 *
 * @author zhouzhuo810
 */
public class LanguageUtil {
    
    
    private static BaseApplication mApp;
    private static Map<Integer, Locale> mSupportLanguages;
    
    public static void init(BaseApplication app) {
        if (app.getSupportLanguages() == null && app.shouldSupportMultiLanguage()) {
            throw new UnsupportedOperationException("Please invoke method #getSupportLanguages() in your application.");
        }
        mApp = app;
    }
    
    /**
     * 设置应用全局语言
     *
     * @param language 语言
     */
    public static void setGlobalLanguage(Integer language) {
        if (mApp == null) {
            return;
        }
        SpUtil.putInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, language);
        updateApplicationLanguage();
    }
    
    
    /**
     * 获取当前设置语言的Integer值
     *
     * @param defaultValue 默认语言
     */
    public static Integer getCurrentLanguage(Integer defaultValue) {
        return SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, defaultValue);
    }
    
    /**
     * 更新Application Context 的Locale
     */
    public static void updateApplicationLanguage() {
        Resources resources = BaseUtil.getApp().getResources();
        Configuration config = resources.getConfiguration();
        Integer code = SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, -1);
        Locale locale = LanguageUtil.getSupportLanguage(code);
        if (locale == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            Locale.setDefault(locale);
        } else {
            config.setLocale(locale);
        }
        BaseUtil.init(BaseUtil.getApp().createConfigurationContext(config), true);
    }
    
    public static Context attachBaseContext(Context context, Integer language, Map<Integer, Locale> supportLanguages) {
        mSupportLanguages = supportLanguages;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, language);
        } else {
            return applyLanguage(context, language);
        }
    }
    
    public static Context attachBaseContext(Context context, Integer language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, language);
        } else {
            return applyLanguage(context, language);
        }
    }
    
    private static Context applyLanguage(Context context, Integer newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(newLanguage);
        configuration.setLocale(locale);
        return context;
    }
    
    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, Integer language) {
        Resources resources = context.getResources();
        final Configuration configuration = resources.getConfiguration();
        Locale locale;
        if (language < 0) {
            //如果没有指定语言使用系统首选语言
            locale = getSystemPreferredLanguage();
        } else {
            //指定了语言使用指定语言，没有则使用首选语言
            locale = getSupportLanguage(language);
        }
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
    
    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(Integer language) {
        if (mApp == null) {
            if (mSupportLanguages == null) {
                return false;
            } else {
                return mSupportLanguages.containsKey(language);
            }
        }
        return mApp.getSupportLanguages().containsKey(language);
    }
    
    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSupportLanguage(Integer language) {
        if (isSupportLanguage(language)) {
            if (mSupportLanguages != null) {
                return mSupportLanguages.get(language);
            }
            return mApp.getSupportLanguages().get(language);
        }
        return getSystemPreferredLanguage();
    }
    
    /**
     * 获取系统首选语言
     *
     * @return Locale
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Locale getSystemPreferredLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
