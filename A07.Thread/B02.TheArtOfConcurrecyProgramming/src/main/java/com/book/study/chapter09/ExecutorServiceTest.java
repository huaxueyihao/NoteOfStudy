package com.book.study.chapter09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {

    public static void main(String[] args) {


//        test01();
//        test02();

        test03();


    }

    private static void test03() {
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    private static void test02() {
        retry:
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(i == 5){
                continue retry;
            }

//            while (i == 5) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("retry i=" + i);
//                continue retry;
//            }

            System.out.println("i=" + i);
        }
    }

    private static void test01() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("hello world");
            }
        });
    }
}
