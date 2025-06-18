package com.minio.console.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

public class ThreadPoolUtils {
    private static volatile boolean isShutdown = false;
    //        ThreadPoolExecutor(int corePoolSize核心线程池数量, int maximumPoolSize最大线程池数量, long keepAliveTime临时线程池存活时间,
    //        java.util.concurrent.TimeUnit unit临时线程存活时间单位,
    //        java.util.concurrent.BlockingQueue<java.lang.Runnable> workQueue线程池满的时候，溢出排队的地方,
    //        java.util.concurrent.ThreadFactory threadFactory指定线程池工厂,
    //        java.util.concurrent.RejectedExecutionHandler handler设置排队的地方也满了之后，对还来的任务做出回应的处理)
    static ExecutorService pool = new ThreadPoolExecutor(8, 8, 8, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    static ThreadPoolExecutor pool2 = new ThreadPoolExecutor(8, 8, 8, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void lambda() throws ExecutionException, InterruptedException {
        Future<?> submit = pool.submit(() -> lambdaMethod());
        System.out.println(submit.get());
    }

    private static String lambdaMethod() {
        return "hello word";
    }

    public static void execute(Runnable runnable) {
        System.out.println("开始执行");
        if (isShutdown) {
            System.out.println("线程池已经关闭");
        }
        pool.execute(() -> execute(runnable));
    }

    public static Future submit(Callable callable) {
        if (isShutdown) {
            System.out.println("线程池已经关闭");
        }
        return pool.submit(callable);
    }

    public static void shutdownNow() {
        isShutdown = true;
        pool.shutdownNow();
    }

    public static void shutdown() {
        isShutdown = true;
        pool.shutdown();
    }
}
