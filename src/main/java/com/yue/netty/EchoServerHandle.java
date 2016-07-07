package com.yue.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * Created by yue on 2016/7/5 业务逻辑处理 handle
 *
 * @see io.netty.channel.Channel
 */
@ChannelHandler.Sharable //表明一个handle 可以被多个channel共享
public class EchoServerHandle extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {//收到消息时
        ByteBuf in = (ByteBuf) msg;
        System.out.println("server received data :" + in.toString(CharsetUtil.UTF_8));//输出到控制台
        ctx.write(in);//向客户端写数据 不刷新输出信息
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);//刷新挂起的数据关闭通道
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印异常信息
        ctx.close();//关闭通道
    }
}
