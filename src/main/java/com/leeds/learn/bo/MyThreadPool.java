/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.test;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
 * 手写线程池，本质上是生产消费者模式。
 * 这里使用得堵塞队列来实现，线程池的使用者是生产者，线程池本身是消费者。
 *
 * @author Leeds, 2021-08-16
 * @version Lee v1.0.
 */
public class MyThreadPool {
    private BlockingQueue<Runnable> queue;
    private MyThread[] myThreads;

    public MyThreadPool(int coreNum, BlockingQueue<Runnable> queue, String name){
        this.queue = queue;
        if (coreNum > Integer.MAX_VALUE){
            coreNum = Integer.MAX_VALUE;
        }
        myThreads = new MyThread[coreNum];
        for (int i = 0; i < coreNum; i++) {
            myThreads[i] = new MyThread(name+"-"+i);
            myThreads[i].start();
            System.out.println(myThreads[i].getName()+"启动了");
        }

    }

    public void execute(Runnable runnable){
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    class MyThread extends Thread{
        MyThread(String name){
            super(name);
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    System.out.println(Thread.currentThread().getName()+"正从堵塞队列取任务======>");
                    Runnable take = queue.take();
                    if(Objects.nonNull(take)){
                        take.run();
                    }
                    // help gc
                    take = null;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);
        MyThreadPool myThreadPool = new MyThreadPool(3, queue, "LeePool");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThreadPool.execute(new MyTask("AAAA"));
        myThreadPool.execute(new MyTask("BBBB"));
        myThreadPool.execute(new MyTask("CCCC"));
        myThreadPool.execute(new MyTask("DDDD"));
        myThreadPool.execute(new MyTask("EEEE"));
        myThreadPool.execute(new MyTask("FFFF"));
    }
}
