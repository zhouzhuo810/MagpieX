package me.zhouzhuo810.magpiex.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.cons.Cons;

public class LanguageUtil {
    
    
    public static final int SIMPLE_CHINESE = 0;
    public static final int TRADITIONAL_CHINESE = 1;
    public static final int ENGLISH = 2;
    public static final int VI = 3;
    
    @IntDef({SIMPLE_CHINESE, TRADITIONAL_CHINESE, ENGLISH, VI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LANGUAGE {
    }
    
    
    private static Map<String, Locale> mSupportLanguages = new HashMap<String, Locale>(4) {{
        put(Cons.SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(Cons.TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
        put(Cons.ENGLISH, Locale.ENGLISH);
        put(Cons.VI, new Locale("vi"));
    }};
    
    
    /**
     * 设置应用全局语言
     *
     * @param language 语言
     *                 <ul>
     *                 <li>{@link LanguageUtil#SIMPLE_CHINESE}</li>
     *                 <li>{@link LanguageUtil#TRADITIONAL_CHINESE }</li>
     *                 <li>{@link LanguageUtil#ENGLISH }</li>
     *                 <li>{@link LanguageUtil#VI }</li>
     *                 </ul>
     */
    public static void setGlobalLanguage(@LanguageUtil.LANGUAGE int language) {
        SpUtil.putInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE, language);
        updateApplicationLanguage();
    }
    
    public static void updateApplicationLanguage() {
        Resources resources = BaseUtil.getApp().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        int code = SpUtil.getInt(Cons.SP_KEY_OF_CHOOSED_LANGUAGE);
        Locale locale = null;
        switch (code) {
            case LanguageUtil.SIMPLE_CHINESE:
                locale = LanguageUtil.getSupportLanguage(Cons.SIMPLIFIED_CHINESE);
                break;
            case LanguageUtil.TRADITIONAL_CHINESE:
                locale = LanguageUtil.getSupportLanguage(Cons.TRADITIONAL_CHINESE);
                break;
            case LanguageUtil.ENGLISH:
                locale = LanguageUtil.getSupportLanguage(Cons.ENGLISH);
                break;
            case LanguageUtil.VI:
                locale = LanguageUtil.getSupportLanguage(Cons.VI);
                break;
        }
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
        Locale locale = null;
        switch (code) {
            case LanguageUtil.SIMPLE_CHINESE:
                locale = LanguageUtil.getSupportLanguage(Cons.SIMPLIFIED_CHINESE);
                break;
            case LanguageUtil.TRADITIONAL_CHINESE:
                locale = LanguageUtil.getSupportLanguage(Cons.TRADITIONAL_CHINESE);
                break;
            case LanguageUtil.ENGLISH:
                locale = LanguageUtil.getSupportLanguage(Cons.ENGLISH);
                break;
            case LanguageUtil.VI:
                locale = LanguageUtil.getSupportLanguage(Cons.VI);
                break;
        }
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
    
    public static void applyLanguage(Context context, String newLanguage) {
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
    
    public static Context attachBaseContext(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, language);
        } else {
            applyLanguage(context, language);
            return context;
        }
    }
    
    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, String language) {
        Resources resources = context.getResources();
        final Configuration configuration = resources.getConfiguration();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        Locale locale;
        if (TextUtils.isEmpty(language)) {//如果没有指定语言使用系统首选语言
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
     * 获取当前系统语言,用于接口请求参数，系统需要的中文标识为zh，而此处返回的是ch，因为接口定的是ch
     *
     * @return 只返回中（ch）英（en）文，如果不是英文，其它情况一律中文
     */
    public static String getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = BaseUtil.getApp().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = BaseUtil.getApp().getResources().getConfiguration().locale;
        }
        
        if (locale == null) {
            return "ch";
        } else if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
            return "en";
        } else {
            return "ch";
        }
    }
    
    
    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(String language) {
        return mSupportLanguages.containsKey(language);
    }
    
    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mSupportLanguages.get(language);
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
