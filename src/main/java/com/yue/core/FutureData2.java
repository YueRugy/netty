package com.yue.core;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yue on 2016/6/29. 异步回调 观察者
 */
public class FutureData2 implements Observer, Data {
    private volatile RealData2 realData2 = null;
    private final Object mutex = new Object();//用于线程同步

    public boolean isFinished() {
        return realData2 != null;
    }


    public String getContent() {
        synchronized (mutex) {
            while (isFinished()) {//如果没有
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return realData2.getContent();
        }

    }

    public void update(Observable realData, Object event) {
        System.out.println("通知...." + event);
        if (!(realData instanceof RealData2)) {
            throw new IllegalArgumentException("主题的数据类型必须是RealData2");
        }
        if (!(event instanceof String)) {
            throw new IllegalArgumentException("事件的数据类型必须是String");
        }
        synchronized (mutex) {
            if (isFinished()) {
                mutex.notifyAll();//如果数据准备好 唤醒线程直接返回可直接返回
                return;
            }
            if ("Finished".equals(event)) {
                realData2 = (RealData2) realData;//数据准备好的时候通知可以返回了
                mutex.notifyAll();//唤醒线程
            }
        }
    }
}
