/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MyThreadPool.
 *
 * @author Leeds, 2021-08-09
 * @version Lee v1.0.
 */
public class MyThreadPool {

    public static void main(String[] args) {
        //
        ThreadFactory threadFactory = new CustomizableThreadFactory("lee线程池-");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 6, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30), threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.execute(new MyRunnable());
        }
        threadPoolExecutor.shutdown();
        try {
            Thread.sleep(500);
            System.out.println("=======暂停");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 不会执行了
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new MyRunnable());
        }

        /*fun1();
        fun2();*/
    }

    private static void fun2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MyCallable myCallable = new MyCallable();
        FutureTask futureTask = new FutureTask<>(myCallable);
        executorService.execute(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void fun1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MyCallable myCallable = new MyCallable();
        Future<?> submit = executorService.submit(myCallable);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
