package com.yue.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

/**
 * Created by yue on 2016/7/1
 */
public class TCPServer {
    protected Selector selector;
    protected Charset charset = Charset.forName("UTF-8");
    protected CharsetDecoder decoder = charset.newDecoder();
    protected CharsetEncoder encoder = charset.newEncoder();
    int count = 1;

    public TCPServer() {
        this(8888);
    }

    public TCPServer(int port) {
        try {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            ServerSocket socket = channel.socket();
            channel.configureBlocking(false);
            socket.bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    handleSelectionKey(key);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSelectionKey(SelectionKey key) {
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            try {
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                Socket socket = sc.socket();
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                SocketAddress address = socket.getRemoteSocketAddress();
                sk.attach("第 " + (count++) + " 个客户端 [" + address + "]");
                System.out.println(sk.attachment() + "连接成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  if (key.isReadable()) {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                int len = channel.read(buffer);
                while (len != -1) {
                    buffer.flip();
                    String msg = decoder.decode(buffer).toString();
                    System.out.println(key.attachment() + msg);
                    String newMsg = "****************";
                    ByteBuffer bt = encoder.encode(CharBuffer.wrap(newMsg + "\n"));
                    channel.write(bt);
                }
                System.out.println(key.attachment() + " 已关闭连接");
                channel.close();
               /* if (len > 0) {
                    buffer.flip();
                    String msg = decoder.decode(buffer).toString();
                    System.out.println(key.attachment() + " ：" + msg);

                    // 根据客户端的消息，查找到对应的输出
                    String newMsg = "****************";
                    ByteBuffer bt = encoder.encode(CharBuffer.wrap(newMsg + "\n"));
                    channel.write(bt);*/

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } if (key.isConnectable()) {
            System.out.println("conn..........");
        }  if (key.isWritable()) {
            System.out.println("write.........");
        } else {
            System.out.println("else................");
        }
    }

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();//这个测试时一定要先启动
    }
}
