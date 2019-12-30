package com.book.study.chapter05;

public class Do2 {

    // 运行结果会不一样
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        thread.interrupt();

        System.out.println("第一次调用thread.isInterrupted():" + thread.isInterrupted());
        System.out.println("第二次调用thread.isInterrupted():" + thread.isInterrupted());

        /**
         * interrupted方法是静态方法，作用于当前线程，当前线程是main，被线程显然没有被中断
         *
         */
        System.out.println("第一次调用thread.interrupted():" + thread.interrupted());
        System.out.println("第二次调用thread.interrupted():" + thread.interrupted());
        System.out.println("thread是否存活：" + thread.isAlive());


    }

}
