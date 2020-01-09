package com.book.study.chapter08;

/**
 * join是用于当前执行线程等待join线程执行结束，原理是不停检查join线程是否存活，
 * 如果join线程存活则让当前线程等待。其中，wait(0)表示永远等待下去，
 * while(isAlive){
 *     wait(0);
 * }
 */
public class JoinCountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        Thread parser1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("parser1 finish");
            }
        });


        Thread parser2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("parser2 finish");
            }
        });

        parser1.start();
        parser2.start();

        parser1.join();
        parser2.join();

        System.out.println("all parser finish");

    }


}
