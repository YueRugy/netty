package com.yue.callback;

/**
 * Created by yue on 2016/7/6
 */
public class Boss {
    private String name;

    public Boss(String name) {
        this.name = name;
    }

    public void getWorkEvent(Worker worker, Event event) {
        System.out.println("老板接收到事件信息： " + worker.getName() + ": " + event.happenEvent());
    }

    public void sendTaskToWorker(Worker worker, Event event) {
        worker.doWork(this, event);
    }

    public static void main(String[] args) {
        Boss boss = new Boss("boss");
        Worker worker1 = new Worker("worker1");
        Worker worker2 = new Worker("worker2");
        Event a = new EventA();
        Event b = new EventB();
        boss.sendTaskToWorker(worker1, a);
        boss.sendTaskToWorker(worker2, b);
    }
}
