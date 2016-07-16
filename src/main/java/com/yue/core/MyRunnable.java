package com.yue.core;

/**
 * Created by yue on 2016/7/15
 */
public class MyRunnable implements Runnable {
    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(this.name + " is running.");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
