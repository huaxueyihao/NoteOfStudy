package com.book.study.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteTest {

    public static void main(String[] args) {

        final ReadWriteFac fac = new ReadWriteFac();
        Thread thread = new Thread();

        for (int i = 0; i < 5; i++) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        fac.get();
                    }
                }
            }, "读线程" + i);
            t1.setPriority(10);
            t1.start();
        }

        for (int i = 6; i < 16; i++) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        fac.plus();
                    }
                }
            }, "写线程" + i);
            t1.setPriority(1);
            t1.start();
        }

    }


    static class ReadWriteFac {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private volatile int i = 0;

        Lock r = lock.readLock();
        Lock w = lock.writeLock();

        public void plus() {
            w.lock();
            System.out.println(Thread.currentThread().getName() + "----获取了写锁");
            try {
                i++;
                System.out.println(Thread.currentThread().getName() + " ----将i修改为" + i);
                r.lock();
                System.out.println(Thread.currentThread().getName() + "----获取了读锁");
            } finally {
                w.unlock();// 释放写锁 因为上面读锁为被释放 其他写线程无法进入但读线程可以继续
                System.out.println(Thread.currentThread().getName() + "----释放了写锁\r\n\r\n");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                }
                r.unlock();// 释放读锁
                System.out.println(Thread.currentThread().getName() + "---释放了读锁");
            }
        }

        public int get() {
            r.lock();
            try {
                if (i != 10) {
                    System.out.println(Thread.currentThread().getName() + "获取到了" + i);
                }
                return i;
            } finally {
                r.unlock();
            }
        }


    }


}
