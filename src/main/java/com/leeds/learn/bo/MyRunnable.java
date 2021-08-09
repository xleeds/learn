/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

/**
 * MyRunnable.
 *
 * @author Leeds, 2021-08-09
 * @version Lee v1.0.
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("我是线程："+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        for (int i = 0; i < 10; i++) {
            new Thread(myRunnable, "线程-"+i).start();
        }
    }
}
