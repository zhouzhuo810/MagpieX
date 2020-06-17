package me.zhouzhuo810.magpiex.utils.thread;

import android.util.SparseArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步线程控制类
 *
 * @author 汪高皖
 */
@SuppressWarnings("ALL")
public class AsyncExecutor {
    private static final AsyncExecutor INSTANCE = new AsyncExecutor();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MAX_FIX_POOL_SIZE = CPU_COUNT;
    private static final int MAX_CACHE_POOL_SIZE = 2 * CPU_COUNT + 1;
    private static final int SCHEDULED_CORE_POOL_SIZE = CPU_COUNT;
    private static final int MAX_SCHEDULED_POOL_SIZE = 2 * CPU_COUNT + 1;
    /**
     * 任务队列最多可缓存任务数量
     */
    private static final int TASK_QUEUE_CACHE_SIZE = 80;
    
    private SparseArray<ExecutorService> mExecutorMap;
    private static final Object LOCK = new Object();
    
    /**
     * 默认获取一个最大线程数量为{@link #MAX_CACHE_POOL_SIZE}的线程池
     *
     * @return ExecutorService
     */
    public static ExecutorService getInstance() {
        return getInstance(ThreadPoolType.CACHED);
    }
    
    /**
     * 根据线程池类型获取不同类型的线程池，如果指定类型不对，则返回null
     *
     * @param poolType 参考{@link ThreadPoolType#FIXED}、
     *                 {@link ThreadPoolType#CACHED}、
     *                 {@link ThreadPoolType#SCHEDULED}、
     *                 {@link ThreadPoolType#SINGLE}
     * @return 返回Executor对象，可根据传入的线程池类型自行强转成对应类型
     */
    public static ExecutorService getInstance(@ThreadPoolType int poolType) {
        ExecutorService executor = INSTANCE.getExecutor(poolType);
        if (executor == null || executor.isShutdown()) {
            synchronized (LOCK) {
                executor = INSTANCE.getExecutor(poolType);
                if (executor == null || executor.isShutdown()) {
                    if (executor != null) {
                        //将已经Shutdown的资源释放,让GC回收
                        INSTANCE.mExecutorMap.remove(poolType);
                        executor = null;
                    }
                    executor = INSTANCE.newExecutor(poolType);
                    INSTANCE.addExecutor(poolType, executor);
                }
            }
        }
        return executor;
    }
    
    private AsyncExecutor() {
        mExecutorMap = new SparseArray();
    }
    
    private ExecutorService getExecutor(@ThreadPoolType int poolType) {
        return mExecutorMap.get(poolType);
    }
    
    private void addExecutor(@ThreadPoolType int poolType, ExecutorService executor) {
        mExecutorMap.put(poolType, executor);
    }
    
    /**
     * 根据线程池类型创建线程池
     *
     * @param poolType 参考{@link ThreadPoolType}类
     * @return Executor对象
     */
    private ExecutorService newExecutor(@ThreadPoolType int poolType) {
        ExecutorService executorService = null;
        switch (poolType) {
            case ThreadPoolType.FIXED:
                executorService = new ThreadPoolExecutor(
                    MAX_FIX_POOL_SIZE,
                    MAX_FIX_POOL_SIZE,
                    0L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(TASK_QUEUE_CACHE_SIZE),
                    new DefaultThreadFactory("fix"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
                break;
            
            case ThreadPoolType.CACHED:
                executorService = new ThreadPoolExecutor(
                    0,
                    MAX_CACHE_POOL_SIZE,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(),
                    new DefaultThreadFactory("cache"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
                break;
            
            case ThreadPoolType.SCHEDULED:
                ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                    SCHEDULED_CORE_POOL_SIZE,
                    new DefaultThreadFactory("scheduled"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
                executor.setMaximumPoolSize(MAX_SCHEDULED_POOL_SIZE);
                executor.setKeepAliveTime(0, TimeUnit.SECONDS);
                executorService = executor;
                break;
            
            case ThreadPoolType.SINGLE:
                executorService = new ThreadPoolExecutor(
                    1,
                    1,
                    0L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(TASK_QUEUE_CACHE_SIZE),
                    new DefaultThreadFactory("single"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
                break;
        }
        return executorService;
    }
    
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        
        DefaultThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
            namePrefix = poolName + " pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
        }
        
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    /**
     * 管理线程池
     */
    public static final class Manager {
        /**
         * 关闭ThreadPoolType中指定类型的线程池，但是会等到线程池中线程执行完毕，之后不会创建新的线程
         *
         * @param threadPoolType 指定要关闭的线程池类型
         */
        public static void shutdown(@ThreadPoolType int threadPoolType) {
            ExecutorService service = INSTANCE.mExecutorMap.get(threadPoolType);
            if (service == null) {
                return;
            }
            
            if (service.isShutdown()) {
                INSTANCE.mExecutorMap.remove(threadPoolType);
                return;
            }
            
            service.shutdown();
            INSTANCE.mExecutorMap.remove(threadPoolType);
        }
        
        /**
         * 关闭ThreadPoolType中指定类型的线程池，正在执行的线程会被立即终止，之后不会创建新的线程
         *
         * @param threadPoolType 指定要关闭的线程池类型
         */
        public static void shutdownNow(@ThreadPoolType int threadPoolType) {
            ExecutorService service = INSTANCE.mExecutorMap.get(threadPoolType);
            if (service == null) {
                return;
            }
            
            if (service.isShutdown()) {
                INSTANCE.mExecutorMap.remove(threadPoolType);
                return;
            }
            
            service.shutdownNow();
            INSTANCE.mExecutorMap.remove(threadPoolType);
        }
        
        /**
         * 关闭所有已经创建的线程池，但是会等到线程池中线程执行完毕
         */
        public static void shutdownAll() {
            if (INSTANCE.mExecutorMap.size() <= 0) {
                return;
            }
            
            for (int i = 0; i < INSTANCE.mExecutorMap.size(); i++) {
                ExecutorService service = INSTANCE.mExecutorMap.get(i);
                if (service == null || service.isShutdown()) {
                    continue;
                }
                service.shutdown();
            }
            
            INSTANCE.mExecutorMap.clear();
        }
        
        /**
         * 关闭所有已经创建的线程池，正在执行的线程会被立即终止
         */
        public static void shutdownNowAll() {
            if (INSTANCE.mExecutorMap.size() <= 0) {
                return;
            }
            for (int i = 0; i < INSTANCE.mExecutorMap.size(); i++) {
                ExecutorService service = INSTANCE.mExecutorMap.get(i);
                if (service == null || service.isShutdown()) {
                    continue;
                }
                service.shutdownNow();
            }
            
            INSTANCE.mExecutorMap.clear();
        }
    }
}


