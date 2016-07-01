package com.yue.core;

/**
 * Created by yue on 2016/7/1.  server
 */
public class HostServer2 {
    public Data request(final int count, final char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");
        final FutureData2 data = new FutureData2();
        new Thread() {
            @Override
            public void run() {
                RealData2 rd = new RealData2();
                rd.addObserver(data);
                rd.createRealData2(c, count);
            }
        }.start();
        System.out.println("    request(" + count + ", " + c + ") END");
        return data;
    }
}
