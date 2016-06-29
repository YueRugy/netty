package com.yue.core;

import java.util.Observable;

/**
 * Created by yue on 2016/6/29. 异步回调通过观察者模式
 */
public class RealData2 extends Observable implements Data {
    private String content;

    public void createRealData2(char c, int count) {
        System.out.println(" making RealData(" + count + ", " + c + ") BEGIN");
        char[] buffer = new char[]{};
        for (int i = 0; i < count; i++) {
            buffer[i] = c;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        content = new String(buffer);
        setChanged();
        notifyObservers("Finished");
    }


    public String getContent() {
        return content;
    }
}
