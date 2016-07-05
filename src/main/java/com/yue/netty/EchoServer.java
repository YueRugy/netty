package com.yue.netty;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private final int port = 8088;

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.start();

    }

    public void start() {
        final EchoServerHandle serverHandle = new EchoServerHandle();//创建

    }

    /***
     * blocking io example
     */
    public void run1() {
        try {
            ServerSocket socket = new ServerSocket(9001);
            Socket client = socket.accept();// blocking
            //从这个socket中导出stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            String request, response;
            while ((request = in.readLine()) != null) {//循环读取客户端数据
                if ("done".equals(request)) {
                    break;
                }
                response = request;//服务端处理客户端传来的数据
                out.print(response);//响应客户端
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
