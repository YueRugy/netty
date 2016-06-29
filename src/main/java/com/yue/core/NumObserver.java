package com.yue.core;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yue on 2016/6/29. 创建观察者
 */
public class NumObserver implements Observer {
    public void update(Observable o, Object arg) {
        //arg这个参数是被观察者调用notifyObservers() 或者notifyObservers(object org)的参数
        //notifyObservers() 会调用重载方法notifyObservers(null) 里面参数是null
        NumObservable myNum = (NumObservable) o;
        System.out.println("data is :" + myNum.getData());

    }
}
