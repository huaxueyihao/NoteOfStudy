package com.netty.study.chapter02;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NioServerCreateStep {
    public static void main(String[] args) throws IOException {

        int port = 8080;

        ServerSocketChannel acceptorSvr = ServerSocketChannel.open();

        acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));
        acceptorSvr.configureBlocking(false);

        Selector selector = Selector.open();
        new Thread(new ReactorTask()).start();

        Object ioHandler = new Object();
        acceptorSvr.register(selector, SelectionKey.OP_ACCEPT, ioHandler);

        int num = selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator it = selectionKeys.iterator();
        while (it.hasNext()) {
            SelectionKey key = (SelectionKey) it.next();
            // ... deal with I/O event ...
        }


        SocketChannel channel = acceptorSvr.accept();

        channel.configureBlocking(false);
        channel.socket().setReuseAddress(true);

        SelectionKey key = channel.register(selector, SelectionKey.OP_READ, ioHandler);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int readNumber = channel.read(byteBuffer);


        List<Object> messageList = new ArrayList<Object>();
        Object message = null;
        while (byteBuffer.hasRemaining()) {
            byteBuffer.mark();
            message = decode(byteBuffer);
            if (message == null) {
                byteBuffer.reset();
                break;
            }
            messageList.add(message);

        }

        if(!byteBuffer.hasRemaining()){
            byteBuffer.clear();
        }else {
            byteBuffer.compact();
        }

        if(messageList != null && !messageList.isEmpty()){
            for (Object messageE : messageList) {
                handlerTask(messageE);
            }
        }
        channel.write(byteBuffer);
    }

    private static void handlerTask(Object messageE) {

    }

    private static Object decode(ByteBuffer byteBuffer) {
        return null;
    }
}
