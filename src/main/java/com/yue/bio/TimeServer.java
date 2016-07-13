package com.yue.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yue on 2016/7/11
 * server
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time server is start port==" + port);
            while (true) {
                Socket socket = server.accept();//循环监听客户端连接 //这里会blocking
                new Thread(new TimeServerHandler(socket)).start();//每次有一个新的连接都会开启一个线程
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

}
