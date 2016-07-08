package com.yue.webchat;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by yue on 2016/7/8
 * 第一次请求是的httphandler返回一个html
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String url;
    private static File file;

    public HttpRequestHandler(String url) {
        this.url = url;
    }

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "WebsocketChatClient.html";
            path = !path.contains("file:") ? path : path.substring(5);
            file = new File(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (url.equalsIgnoreCase(request.getUri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {//处理100请求
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                ctx.writeAndFlush(response);
            }

            RandomAccessFile f = new RandomAccessFile(file, "r");//建立新的连接
            HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);//创建一个返回值，也就是responsehead
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");//设置返回的格式和编码
            boolean isAlive = HttpHeaders.isKeepAlive(request);
            if (isAlive) {
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());//设置返回的长度
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);//设置保持连接
            }

            ctx.write(response);//消息头返回
            if (ctx.pipeline().get(SslHandler.class) == null) {//消息body返回也就是一个html
                ctx.write(new DefaultFileRegion(f.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(f.getChannel()));
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!isAlive) {
                future.addListener(ChannelFutureListener.CLOSE);//如果不需要保持连接 关闭通道
            }
        }

    }
}
