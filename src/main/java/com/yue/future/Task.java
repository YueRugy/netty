package com.yue.future;

import java.util.concurrent.Callable;

/**
 * Created by yue on 2016/7/5
 */
public class Task implements Callable<Integer> {
    public Integer call() throws Exception {
        System.out.println("子线程执行任务");
        Thread.sleep(1000);

        int num = 0;
        for (int i = 0; i < 100; i++) {
            num += i;
        }
        return num;
    }
}
