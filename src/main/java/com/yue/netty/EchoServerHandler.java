package com.yue.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

/**
 * Created by yue on 2016/6/27.
 */
public class EchoServerHandler {
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        e.getChannel().write(e.getMessage());

    }

}
