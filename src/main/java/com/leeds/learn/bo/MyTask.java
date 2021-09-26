/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.test;

/**
 * MyTask.
 *
 * @author Leeds, 2021-08-16
 * @version Lee v1.0.
 */
public class MyTask implements Runnable {

    private String name;

    public MyTask(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            System.out.println("线程："+Thread.currentThread().getName()+",正在执行任务："+name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
