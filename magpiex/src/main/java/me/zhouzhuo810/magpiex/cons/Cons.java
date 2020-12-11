package me.zhouzhuo810.magpiex.cons;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * MagpieX框架用到的常量
 *
 * @author zhouzhuo810
 */
public final class Cons {
    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;
    
    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
    
    public static final String SP_KEY_OF_CHOOSED_LANGUAGE = "sp_key_of_choosed_language";
    
    public static final String SP_KEY_OF_TAKE_PHOTO_CAMERA_PIC_PATH = "sp_key_of_take_photo_camera_pic_path";
    
    public static final String SP_NAME = "magpie_sp";
    
    public static final String NOTICE_ACTION = "notice_action";
    
    public static final String NOTICE_URL = "notice_url";
    
    public static final String SP_KEY_OF_CURRENT_ACTIVITY_OR_FRAGMENT_NAME = "sp_key_of_current_activity_or_fragment_name";
}
