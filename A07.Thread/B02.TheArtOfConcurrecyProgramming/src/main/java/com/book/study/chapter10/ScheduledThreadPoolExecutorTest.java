package com.book.study.chapter10;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorTest {


    public static void main(String[] args) {

        test01();

    }

    private static void test01() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);


        scheduledThreadPoolExecutor.schedule(new Runnable() {
            public void run() {
                System.out.println("hello wrold");
            }
        }, 10, TimeUnit.SECONDS);

    }


}
