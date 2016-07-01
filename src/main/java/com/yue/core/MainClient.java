package com.yue.core;


public class MainClient {

    public static void main(String[] args) {
        testHostServer2();
    }

    static void testHostServer2() {
        System.out.println("main BEGIN");
        HostServer2 hostServer2 = new HostServer2();
        Data data1 = hostServer2.request(10, 'A');
        Data data2 = hostServer2.request(20, 'B');
        Data data3 = hostServer2.request(30, 'C');

        System.out.println("main otherJob BEGIN");
        System.out.println("main otherJob END");

        System.out.println("data1 = " + data1.getContent());
        System.out.println("data2 = " + data2.getContent());
        System.out.println("data3 = " + data3.getContent());
        System.out.println("main END");

    }
}
