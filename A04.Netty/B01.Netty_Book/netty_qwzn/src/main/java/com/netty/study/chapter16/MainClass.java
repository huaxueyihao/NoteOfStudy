package com.netty.study.chapter16;

import java.nio.channels.SelectionKey;

public class MainClass {

    public static void main(String[] args) {

//

        // 100
        System.out.println(Integer.toBinaryString(SelectionKey.OP_WRITE));
        // 1
        System.out.println(Integer.toBinaryString(SelectionKey.OP_READ));
        // 10000
        System.out.println(Integer.toBinaryString(SelectionKey.OP_ACCEPT));

        // 1000
        System.out.println(Integer.toBinaryString(SelectionKey.OP_CONNECT));

    }
}
