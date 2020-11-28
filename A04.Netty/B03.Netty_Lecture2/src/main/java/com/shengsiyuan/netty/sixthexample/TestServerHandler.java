package com.shengsiyuan.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
        System.out.println(msg.getName()+" "+msg.getAge()+" "+msg.getAddress());

    }




}
