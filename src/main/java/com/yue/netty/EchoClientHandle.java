package com.yue.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandlerAdapter;

/**
 * Created by yue on 2016/7/5
 */

public class EchoClientHandle extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("netty test test", CharsetUtil.UTF_8));//向服务端传送数据
        /*ctx.channel().write(Unpooled.copiedBuffer("Netty in Action",
                CharsetUtil.UTF_8));  */
       /* ctx.pipeline().write(Unpooled.copiedBuffer("Netty in Action",
                CharsetUtil.UTF_8));*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf temp = (ByteBuf) msg;
        System.out.println("client received" + temp.toString(CharsetUtil.UTF_8));//输出
    }


}
