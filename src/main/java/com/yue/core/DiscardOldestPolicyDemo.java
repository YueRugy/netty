package com.yue.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yue on 2016/7/15
 */
public class DiscardOldestPolicyDemo {
    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 10; i++) {
            Runnable task = new MyRunnable("task -" + i);
            pool.execute(task);
        }

        pool.shutdown();

    }
}
