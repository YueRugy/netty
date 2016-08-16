package com.yue.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * @see java.nio.Buffer
 */
public class EchoServer {
    private final int port = 8088;

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.start();

    }

    public void start() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final EchoServerHandle serverHandle = new EchoServerHandle();//创建业务逻辑类 handle
        EventLoopGroup group = new NioEventLoopGroup();//时间循环组 英语翻译
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()//创建辅助类
                    .group(group,group)//添加group
                    .channel(NioServerSocketChannel.class)//制定一个nio传输通道
                    .localAddress(new InetSocketAddress(port))//用指定的端口设置socket地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //在channel中的pipeline加入ChannelHandler
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {//由于EchoServerHandle
                            // 中指定Sharable 我们可以共用这个serverHandle
                            ch.pipeline().addLast(serverHandle);
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync();//异步绑定服务器知道绑定完成
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().close().sync();//获取这个channel的closeFuture阻塞知道完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();//释放所有资源
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
