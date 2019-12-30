package com.shengsiyuan.nio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

public class NioTest7 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());



    }

}
