package com.yue.core;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yue on 2016/6/29. 偶数观察者
 */
public class EvenObserver implements Observer {
    public void update(Observable o, Object arg) {
        NumsObservable numsObservable = (NumsObservable) o;
        Integer flag = (Integer) arg;
        if (flag == 2) {
            System.out.println("even  ：" + numsObservable.getData());
        }
    }
}
