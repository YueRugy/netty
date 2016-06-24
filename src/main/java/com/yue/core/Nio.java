package com.yue.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/***
 * 简单的例子复习下nio
 * 1 创建一个ServerSocketChannel
 * 2 绑定监听地址
 * 3 启动io线程 创建 selector（选择器）
 * 4
 */
public class Nio {
    private int port;//端口号

    public void socket() {
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();//创建一个ServerSocketChannel
            ssc.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));//绑定监听地址
            ssc.configureBlocking(false);//设置非阻塞

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
