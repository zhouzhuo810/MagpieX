package me.zhouzhuo810.magpiex.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册EventBus工具类，防止重复注册和注销
 *
 * @author zhouzhuo810
 * @date 2021/11/23 9:44 上午
 */
public class EventBusUtil {
    
    public static void register(Object object) {
        EventBus aDefault = EventBus.getDefault();
        if (aDefault.isRegistered(object)) {
            return;
        }
        
        aDefault.register(object);
    }
    
    public static void unregister(Object object) {
        EventBus aDefault = EventBus.getDefault();
        if (!aDefault.isRegistered(object)) {
            return;
        }
        
        aDefault.unregister(object);
    }
    
    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }
}
