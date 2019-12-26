package com.jdk8.study.Queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueTest {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String value = "你好" + i;
                    try {
                        queue.put(value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("生产：" + value);
                }

            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (queue.size() <= 0) {
                        break;
                    }
                    String value = queue.poll();
                    System.out.println("消费:" + value);
                }

            }
        }).start();
    }
}
