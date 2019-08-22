package me.zhouzhuo810.magpiex.cons;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Cons {
    //    public static final String
    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

    // 简体中文
    public static final String SIMPLIFIED_CHINESE = "zh";
    // 英文
    public static final String ENGLISH = "en";
    //越南文
    public static final String VI = "vi";
    // 繁体中文
    public static final String TRADITIONAL_CHINESE = "zh-hant";

    public static final String SP_KEY_OF_CHOOSED_LANGUAGE = "sp_key_of_choosed_language";
    public static final String SP_KEY_OF_TAKE_PHOTO_CAMERA_PIC_PATH = "sp_key_of_take_photo_camera_pic_path";
    
    public static final String SP_NAME = "magpie_sp";
    
    public static final String NOTICE_ACTION = "notice_action";
    
    public static final String NOTICE_URL = "notice_url";
}
