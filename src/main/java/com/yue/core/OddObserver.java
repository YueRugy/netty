package com.yue.core;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yue on 2016/6/29. 奇数观察者
 */
public class OddObserver implements Observer {

    public void update(Observable o, Object arg) {
        NumsObservable numsObservable = (NumsObservable) o;
        Integer flag = (Integer) arg;
        if (flag == 1) {
            System.out.println("odd  ：" + numsObservable.getData());
        }
    }
}
