/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * MyCallable.
 *
 * @author Leeds, 2021-08-09
 * @version Lee v1.0.
 */
public class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        for (int i = 0; i < 10; i++) {
            FutureTask futureTask = new FutureTask<>(myCallable);
            new Thread(futureTask, "线程-"+i).start();
            try {
                System.out.println("我是线程："+futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
