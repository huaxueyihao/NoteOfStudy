package com.book.study.chapter05;

public class Do3 {

    /**
     * 第一次interrupted：true
     * 第二次interrupted：false
     * @param args
     */
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        System.out.println("第一次调用Thread.currentThread().interrupt()："
                +Thread.currentThread().isInterrupted());
        System.out.println("第一次调用thread.interrupted()："
                +Thread.currentThread().interrupted());
        System.out.println("第二次调用thread.interrupted()："
                +Thread.currentThread().interrupted());

    }

}
