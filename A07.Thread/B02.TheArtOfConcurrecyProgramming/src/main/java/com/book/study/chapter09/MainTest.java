package com.book.study.chapter09;

import sun.applet.Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }



    public static void main(String[] args) {


        MainTest mainTest = new MainTest();

        System.out.println("ctl="+mainTest.ctl.get());
        System.out.println("COUNT_BITS="+COUNT_BITS);
        System.out.println("CAPACITY="+CAPACITY);

        System.out.println("RUNNING="+RUNNING);
        System.out.println("SHUTDOWN="+SHUTDOWN);
        System.out.println("STOP="+STOP);
        System.out.println("TIDYING="+TIDYING);
        System.out.println("TERMINATED="+TERMINATED);


        System.out.println((CAPACITY-1) & CAPACITY);



        System.out.println("ctl="+mainTest.ctl.get());
        System.out.println("COUNT_BITS="+COUNT_BITS);
        System.out.println("CAPACITY="+CAPACITY);

        // 1110 0000 0000 0000 0000 0000 0000 0000
        System.out.println("RUNNING="+(Integer.toBinaryString(RUNNING)));
        // 0
        System.out.println("SHUTDOWN="+Integer.toBinaryString(SHUTDOWN));
        // 0010 0000 0000 0000 0000 0000 0000 0000
        System.out.println("STOP="+Integer.toBinaryString(STOP));
        // 0100 0000 0000 0000 0000 0000 0000 0000
        System.out.println("TIDYING="+Integer.toBinaryString(TIDYING));
        // 0110 0000 0000 0000 0000 0000 0000 0000
        System.out.println("TERMINATED="+Integer.toBinaryString(TERMINATED));


    }
}
