package me.zhouzhuo810.magpiex.utils.thread;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 线程池类型
 * @author 汪高皖
 */
@IntDef({ThreadPoolType.FIXED, ThreadPoolType.CACHED, ThreadPoolType.SCHEDULED, ThreadPoolType.SINGLE})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadPoolType{
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */
    int FIXED = 0;
    
    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
     */
    int CACHED = 1;
    
    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     * <P>建议：所有周期执行的任务采用此线程</P>
     */
    int SCHEDULED = 2;
    
    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     */
    int SINGLE = 3;
}
