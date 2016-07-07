package com.yue.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * Created by yue on 2016/7/7
 *  @see
 */
public class ByteBufTest {
    public static void main(String[] args) {
       /* ByteBuf buffer = Unpooled.copiedBuffer("hello ,netty", CharsetUtil.UTF_8);//堆bytebuf
        if (buffer.hasArray()) {
            byte[] heapByte = buffer.array();
            int offset = buffer.arrayOffset() + buffer.readerIndex();
            int length = buffer.readableBytes();

        }

        ByteBuf buf = Unpooled.directBuffer();//直接缓冲区，如果要用数组的话需要拷贝一个新的byte[]
        if (!buf.hasArray()) {
            byte[] direByte = buf.array();
            byte[] newBytes = new byte[direByte.length];
            buf.getBytes(buf.readerIndex(), newBytes);
        }*/
        //COMPOSITE BUFFER(复合缓冲区)
        //1 Nio 实现
       /* ByteBuffer head = ByteBuffer.allocate(1024);
        ByteBuffer body = ByteBuffer.allocate(1024);
      //  ByteBuffer[] messageArray = new ByteBuffer[]{head, body};
        ByteBuffer message = ByteBuffer.allocate(head.remaining() + body.remaining());
        message.put(head);
        message.put(body);*/

        /*//netty 实现
        byte[] name = new byte[]{10, 11, 12, 13, 14, 14, 1, 22, 3};
        byte[] notice = new byte[]{10, 11, 12, 13, 14, 14, 1, 22, 3, 23};
        ByteBuf head = Unpooled.directBuffer(1024);
        ByteBuf body = Unpooled.buffer(1024);
        head.writeBytes(name);
        body.writeBytes(notice);

        CompositeByteBuf message = Unpooled.compositeBuffer(2048);
        message.addComponents(head, body);
        print(message);
        message.removeComponent(0);
        print(message);*/

        ByteBuf buffer = Unpooled.buffer();
        byte[] message = new byte[]{10, 11, 12, 13, 14, 14, 1, 22, 3};
        buffer.writeBytes(message);
        /*System.out.println(buffer.readableBytes());//get 不会移动readIndex
        for (int i = 0; i < buffer.readableBytes(); i++) {
            System.out.println(buffer.getByte(i));
        }*/

        /*while (buffer.readableBytes() > 0) {
            System.out.println(buffer.readByte());//read会移动readindex
        }*/

        /*while(buffer.isReadable()){
            System.out.println(buffer.readByte());//read会移动readindex
        }*/
      /*  ByteBuf newBuffer = buffer.duplicate(); //派生的buffer和 原来的共享数据
        newBuffer.setByte(2,33);
        while(buffer.isReadable()){
            System.out.println(buffer.readByte());//read会移动readindex
        }*/

    }

    // CompositeByteBuf 和直接内存缓冲区很像，不允许访问可能存在的支持数组，也不能直接访问数据，必须通过copy一个数组
    private static void print(CompositeByteBuf message) {
        for (int i = 0; i < message.numComponents(); i++) {
            int length = message.component(i).readableBytes();
            byte[] temp = new byte[length];
            message.getBytes(message.component(i).readerIndex(), temp);
            System.out.println(Arrays.toString(temp));
            //  System.out.println(message.component(i).toString());
        }
    }
}
