package com.netty.study.chapter07;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client : [" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();// 发生异常，关闭链路
    }
}
