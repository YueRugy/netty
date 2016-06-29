package com.yue.core;

import java.util.Observable;

/**
 * Created by yue on 2016/6/29. 被观察者 奇偶数
 */
public class NumsObservable extends Observable {
    public static final Integer ODD = 1;
    public static final Integer EVEN = 2;

    private int data;


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
        Integer flag = EVEN;
        if ((data & 0x0001) == 1) {
            flag = ODD;
        }
        setChanged();
        notifyObservers(flag);
    }
}
