package com.book.study.chapter05;

public class ThreadTest {


    public static void main(String[] args) throws InterruptedException {

        test01();


    }

    private static void test01() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("Worker started");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Worker IsInterrupted: " + Thread.currentThread().isInterrupted());
                }
                System.out.println("Worker stopped");
            }
        });

        thread.start();

        Thread.sleep(200);
        thread.interrupt();

        System.out.println("Main thread stopped");

//        System.out.println(thread.isInterrupted());


    }

}
