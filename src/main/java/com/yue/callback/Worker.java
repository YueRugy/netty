package com.yue.callback;

/**
 * Created by yue on 2016/7/6
 */
public class Worker {
    private String name;

    public String getName() {
        return name;
    }

    public Worker(String name) {
        this.name = name;
    }

    public void doWork(Boss boss, Event event) {
        System.out.println(name + " is doing working...");
        //工人挺辛苦，干着枯燥乏味的重复工作，哈哈
        for (int i = 0; i < 2000000; i++) {
            int x = i / 234 + 234;
        }
        System.out.println(name + " was finished work.");
        boss.getWorkEvent(this, event);
    }


    public void setName(String name) {
        this.name = name;
    }

}
