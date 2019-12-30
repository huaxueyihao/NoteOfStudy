package com.book.study.chapter05;

public class Do {

    // 运行结果会不一样
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        thread.interrupt();
        
        System.out.println("第一次调用thread.isInterrupted():" + thread.isInterrupted());
        System.out.println("thread是否存活：" + thread.isAlive());
        System.out.println("第二次调用thread.isInterrupted():" + thread.isInterrupted());
        System.out.println("thread是否存活：" + thread.isAlive());


    }

}
