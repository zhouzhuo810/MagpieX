package me.zhouzhuo810.magpiex.utils;

import android.content.Context;

/**
 * 资源文件工具类
 */
public class ResourcesUtil {

    private static final String RES_ID = "id";
    private static final String RES_STRING = "string";
    private static final String RES_DRAWABLE = "drawable";
    private static final String RES_MIPMAP = "mipmap";
    private static final String RES_LAYOUT = "layout";
    private static final String RES_STYLE = "style";
    private static final String RES_COLOR = "color";
    private static final String RES_DIMEN = "dimen";
    private static final String RES_ANIM = "anim";
    private static final String RES_MENU = "menu";

    /**
     * 获取资源文件的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getId(Context context, String resName){
        return getResId(context,resName,RES_ID);
    }

    /**
     * 获取资源文件string的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getStringId(Context context,String resName){
        return getResId(context,resName,RES_STRING);
    }

    /**
     * 获取资源文件drawable的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getDrawableId(Context context,String resName){
        return getResId(context,resName,RES_DRAWABLE);
    }
    /**
     * 获取资源文件mipmap的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getMipmapId(Context context,String resName){
        return getResId(context,resName,RES_MIPMAP);
    }

    /**
     * 获取资源文件layout的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getLayoutId(Context context,String resName){
        return getResId(context,resName,RES_LAYOUT);
    }

    /**
     * 获取资源文件style的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getStyleId(Context context,String resName){
        return getResId(context,resName,RES_STYLE);
    }

    /**
     * 获取资源文件color的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getColorId(Context context,String resName){
        return getResId(context,resName,RES_COLOR);
    }

    /**
     * 获取资源文件dimen的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getDimenId(Context context,String resName){
        return getResId(context,resName,RES_DIMEN);
    }

    /**
     * 获取资源文件ainm的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getAnimId(Context context,String resName){
        return getResId(context,resName,RES_ANIM);
    }

    /**
     * 获取资源文件menu的id
     * @param context Context
     * @param resName 资源名
     * @return 资源id
     */
    public static int getMenuId(Context context,String resName){
        return getResId(context,resName,RES_MENU);
    }

    /**
     * 获取资源文件ID
     * @param context Context
     * @param resName 资源名
     * @param defType 资源类型
     * @return 资源id
     */
    public static int getResId(Context context,String resName,String defType){
        return context.getResources().getIdentifier(resName, defType, context.getPackageName());
    }
}