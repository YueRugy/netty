package com.yue.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by yue on 2016/7/5
 * @see io.netty.bootstrap.AbstractBootstrap;
 * @see Object
 */
public class EchoClient {
    private final int port = 8088;
    private final String host = "127.0.0.1";

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        client.start();
    }


    private void start() {
        final EchoClientHandle handle = new EchoClientHandle();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(handle);
                        }
                    });
            ChannelFuture future = b.connect().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
