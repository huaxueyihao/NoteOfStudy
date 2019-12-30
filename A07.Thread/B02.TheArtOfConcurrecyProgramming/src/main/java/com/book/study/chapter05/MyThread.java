package com.book.study.chapter05;

public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("i=" + (i + 1));
        }
    }
}
