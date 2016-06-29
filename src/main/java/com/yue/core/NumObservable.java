package com.yue.core;

import java.util.Observable;

/**
 * 创建被观察着
 */
public class NumObservable extends Observable {
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
        setChanged();//设置标志位true
        //可以看一下源码 很有意思
        notifyObservers();//内部遍历观察者vector 然后调用观察者的update方法 观察者属于被动调用
    }
}
