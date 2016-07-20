package com.yue.core;

/**
 * Created by yue on 2016/7/20
 */
//锁的重入 ，获取自身锁的同时 会获取父类的锁
public class Widget {
    @Override
    public synchronized String toString() {
        System.out.println("father  ...........");
        return super.toString();
    }
}

class ChildWidget extends Widget {
    public static void main(String[] args) {
        ChildWidget child = new ChildWidget();
        child.toString();
    }

    @Override
    public synchronized String toString() {
        System.out.println("child..............");
        return super.toString();
    }
}
