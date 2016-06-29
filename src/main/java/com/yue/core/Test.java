package com.yue.core;

/**
 * Created by yue on 2016/6/29. 11
 */
public class Test {
    public static void main(String[] args) {
        NumObservable numObservable = new NumObservable();
        //加入观察者
        numObservable.addObserver(new NumObserver());
       /* numObservable.addObserver(new NumObserver());
        numObservable.addObserver(new NumObserver());*/
        numObservable.setData(1);
        numObservable.setData(2);
        numObservable.setData(3);

        NumsObservable second = new NumsObservable();
        second.addObserver(new EvenObserver());
        second.addObserver(new OddObserver());
        second.setData(1);
        second.setData(2);
        second.setData(3);
        second.setData(4);
    }
}
