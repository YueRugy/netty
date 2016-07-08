package com.yue.core;

import java.net.URL;

/**
 * Created by yue on 2016/6/29. 11
 *
 * @see java.util.concurrent.Callable
 * @see Runnable
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.FutureTask
 */
public class Test {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        System.out.println(args[0]);
        System.out.println(Test.class.getProtectionDomain().getCodeSource().getLocation());
       /* int port = Integer.parseInt(args[0]);
        NumObservable numObservable = new NumObservable();
        //加入观察者
        numObservable.addObserver(new NumObserver());
       *//* numObservable.addObserver(new NumObserver());
        numObservable.addObserver(new NumObserver());*//*
        numObservable.setData(1);
        numObservable.setData(2);
        numObservable.setData(3);

        NumsObservable second = new NumsObservable();
        second.addObserver(new EvenObserver());
        second.addObserver(new OddObserver());
        second.setData(1);
        second.setData(2);
        second.setData(3);
        second.setData(4);*/


    }
}
