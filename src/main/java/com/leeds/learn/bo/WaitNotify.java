/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

import java.util.concurrent.TimeUnit;

/**
 * 多线程通信之 依靠对象的等待、通知机制.
 * 生产者和消费者模式
 *
 * @author Leeds, 2021-09-26
 * @version Lee v1.0.
 */
public class WaitNotify {

    static boolean flag = false;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Object lock = new Object();
        /**
         *
         * synchronized (对象){
         *     while(条件不满足){
         *         对象.wait(); //会释放锁
         *     }
         *     执行其他业务
         * }
         *
         */
        Thread waitThread = new Thread(() -> {
            synchronized (lock) {
                while (!flag) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "，1、正在等待。。。");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + "，3、通知来了。。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "，4、接收到信号，正在处理。。。");
        }, "WaitThread");
        waitThread.start();

        // 休息一下
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         *
         * synchronized (对象){
         *     改变判断条件
         *     对象.notify();
         * }
         *
         *
         */
        Thread notifyThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "，2、通知其他人干活。。。");
                lock.notify();
                flag = true;
                // 如果在这里等待，通知会等 等待时间结束之后才生效
            }// 锁放掉之后，通知才生效
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 再次加锁试试
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "，5、再次加锁。。。");
            }
        }, "NotifyThread");
        notifyThread.start();
    }
}
