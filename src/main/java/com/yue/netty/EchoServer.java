package com.yue.netty;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class EchoServer {
    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline((ChannelPipeline) new EchoServerHandler());
            }
        });
        bootstrap.bind(new InetSocketAddress(9001));
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
