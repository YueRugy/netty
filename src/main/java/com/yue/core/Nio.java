package com.yue.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/***
 * 简单的例子复习下nio
 * 1 创建一个ServerSocketChannel
 * 2 绑定监听地址
 * 3 启动io线程 创建 selector（选择器）
 * 4 将ServerSocketChannel 注册到selector上 监听客户端连接
 * 5 轮询 就绪的key
 * 6 handleAccept 处理新的客户端连接
 */
public class Nio {
    private int port;//端口号

    public void socket() {
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();//创建一个ServerSocketChannel 它是所有客户端连接的父通道
            ssc.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));//绑定监听地址
            ssc.configureBlocking(false);//设置非阻塞
            Selector selector = Selector.open();//创建 selector（选择器）
            ssc.register(selector, SelectionKey.OP_ACCEPT);//将ServerSocketChannel注册到selector上监听客户端连接
            //轮询
            int number = selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    //多路复用器 监听到有新的连接接入，处理新的请求与tcp完成三次握手后 与客户端建立建立物理链路
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel ss = channel.accept();
                    //设置客户端链路的参数
                    ss.socket().setReuseAddress(true);
                    ss.configureBlocking(false);
                    //将新接入的客户端连接注册到Reactor的多路复用器上监听读操作位，用来接收客户端发送的消息
                    ss.register(selector, SelectionKey.OP_READ);
                }
            }

            //   new Thread(new Reac()).start();
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
