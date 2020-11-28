package com.zookeeper.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8000));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        // 注册channel，并且制定感兴趣的事件Accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);

        writeBuff.put("received".getBytes());
        writeBuff.flip();

        while (true) {
            int nReady = selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    // 创建新的连接，并且把连接注册到Selector上，而且，
                    // 声明这个channel只对读操作感兴趣

                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuff.clear();
                    socketChannel.read(readBuff);

                    readBuff.flip();
                    System.out.println("received: " + new String(readBuff.array()));
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }





    }

}
