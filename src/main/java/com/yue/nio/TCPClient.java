package com.yue.nio;

import java.io.*;
import java.net.Socket;

/**
 * Created by yue on 2016/7/1
 */
public class TCPClient extends Thread {
    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            // 发送服务器
            out.write(">>>>>>>>>>>>>>>>".getBytes("UTF-8"));
            out.flush();
            // 接收给服务器
            System.out.println(">>>> " + reader.readLine());

            // 发送服务器
            out.write("<<<<<<<<<<<<<<<<".getBytes("UTF-8"));
            out.flush();
            // 接收服务器
            System.out.println(">>>> " + reader.readLine());
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TCPClient().start();
    }
}
