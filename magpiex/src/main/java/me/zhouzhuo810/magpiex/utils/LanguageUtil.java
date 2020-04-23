package me.zhouzhuo810.magpiex.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.app.BaseApplication;
import me.zhouzhuo810.magpiex.cons.Cons;

public class LanguageUtil {
    
    
    private static BaseApplication mApp;
    
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
    public static void setGlobalLanguage(int language) {
        if (mApp == null) {
            return;
        }
        SpUtil.putInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, language);
        updateApplicationLanguage();
    }
    
    
    /**
     * 获取当前设置语言的int值
     *
     * @param defaultValue 默认语言
     */
    public static int getCurrentLanguage(int defaultValue) {
        return SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, defaultValue);
    }
    
    public static void updateApplicationLanguage() {
        Resources resources = BaseUtil.getApp().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        int code = SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE);
        Locale locale = LanguageUtil.getSupportLanguage(code);
        if (locale == null) {
            return;
        }
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            BaseUtil.getApp().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }
    
    public static void updateActivityLanguage(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        int code = SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE);
        Locale locale = LanguageUtil.getSupportLanguage(code);
        if (locale == null) {
            return;
        }
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }
    
    public static void applyLanguage(Context context, int newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(newLanguage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DisplayMetrics dm = resources.getDisplayMetrics();
            // apply locale
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, dm);
        } else {
            // updateConfiguration
            DisplayMetrics dm = resources.getDisplayMetrics();
            configuration.locale = locale;
            resources.updateConfiguration(configuration, dm);
        }
    }
    
    public static Context attachBaseContext(Context context, int language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, language);
        } else {
            applyLanguage(context, language);
            return context;
        }
    }
    
    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, int language) {
        Resources resources = context.getResources();
        final Configuration configuration = resources.getConfiguration();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        Locale locale;
        if (language < 0) {//如果没有指定语言使用系统首选语言
            locale = getSystemPreferredLanguage();
        } else {//指定了语言使用指定语言，没有则使用首选语言
            locale = getSupportLanguage(language);
        }
        configuration.setLocale(locale);
        //        Context configurationContext = context.createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, dm);
        return context;
    }
    
    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(int language) {
        if (mApp == null) {
            return false;
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
    public static Locale getSupportLanguage(int language) {
        if (isSupportLanguage(language)) {
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
