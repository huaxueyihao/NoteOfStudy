package com.jdk8.study.Queue;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

    public static void main(String[] args) {

        DelayQueue<DelayedTask> delayQueue = new DelayQueue<DelayedTask>();

        DelayedTask delayedTask = new DelayedTask(5000, "abc");
        DelayedTask delayedTask1 = new DelayedTask(3000, "def");


        delayQueue.offer(delayedTask);
        delayQueue.offer(delayedTask1);

        long startTime = System.currentTimeMillis();

        while (true) {
            try {
                DelayedTask de = delayQueue.take();
                System.out.println(de);
                long endTime = System.currentTimeMillis();
                System.out.println("用时总时间：" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
