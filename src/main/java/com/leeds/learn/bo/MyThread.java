/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

/**
 * MyThread.
 *
 * @author Leeds, 2021-08-09
 * @version Lee v1.0.
 */
public class MyThread extends Thread {

    public MyThread(String name){
        super.setName(name);
    }


    @Override
    public void run(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是线程："+Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        System.out.println("线程开始了");
        for (int i = 0; i < 10; i++) {
            MyThread myThread = new MyThread("线程-"+i);
            myThread.start();

            // 主线程调用，等待子线程完成后，再运行主线程，并且会顺序执行
            try {
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.currentThread().join(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程结束了");
    }

}
