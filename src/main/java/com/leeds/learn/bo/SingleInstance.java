/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.leeds.learn.bo;

import java.util.Objects;

/**
 * volatile 用于双重检查时的例子.
 *
 * @author Leeds, 2021-07-09
 * @version Lee v1.0.
 */
public class SingleInstance {

    /**
     * 因为new对象的操作为：
     * 1、分配内存空间
     * 2、调用构造器，初始化实例
     * 3、返回地址给引用
     *  其中2和3可能出现指令重排。在多线程环境下，就有可能出现，还未构造完成就返回引用的情况。虽然 singleInstance判断上不为空，但实际
     *  使用singleInstance中的方法就会NPE。
     */
    static volatile SingleInstance singleInstance = null;

    private SingleInstance(){

    }

    /**
     * 双重检查单例
     * @return
     */
    public static SingleInstance getInstance(){
        // 第一次检查，没有初始化的才加锁生成。提高了效率。
        if(Objects.isNull(singleInstance)){
            synchronized (SingleInstance.class){
                // 防止出现 多线程时，第一次检查同时为null，重复创建对象。
                if(Objects.isNull(singleInstance)){
                    singleInstance = new SingleInstance();
                }
            }
        }
        return singleInstance;
    }

    public static void main(String[] args) {
        int x = -123;
        String temp = x+"";
        boolean flag = false;
        if('-' == (temp.charAt(0))){
            temp = temp.substring(1);
            flag = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(temp);
        sb.reverse();
        String a = flag?"-"+sb.toString():sb.toString();
        long b = new Long(a);
        if(b>=Integer.MIN_VALUE && b<=Integer.MAX_VALUE){
            System.out.println(new Integer(a));
        }
        System.out.println(0);
    }
}
