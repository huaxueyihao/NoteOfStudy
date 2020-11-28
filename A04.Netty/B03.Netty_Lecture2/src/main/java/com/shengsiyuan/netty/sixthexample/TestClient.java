package com.shengsiyuan.netty.sixthexample;

import com.shengsiyuan.netty.firstexample.TestServerInitializer;
import com.shengsiyuan.netty.secondexample.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClient {


    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();


//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//            for (; ; ) {
//                channel.writeAndFlush(br.readLine() + "\r\n");
//            }

        } finally {
            eventLoopGroup.shutdownGracefully();
        }


    }


}
