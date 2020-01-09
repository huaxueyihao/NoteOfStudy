package com.book.study.chapter05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {

    static Map<String, Object> map = new HashMap<String, Object>();

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();


    // 获取一个key对应的value
    public static final Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    // 设置key对应的value，并返回旧的value
    public static final Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    // 清空所有内容
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }


    public static void main(String[] args) {

        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        final Lock r = rwl.readLock();
        final Lock w = rwl.writeLock();

        final HashMap map = new HashMap();

        Runnable runnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 3; i++) {
                    r.lock();
                    try {
                        System.out.println("任务1");
                        map.get("x");
                    } finally {
//                        r.unlock();
                    }
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            public void run() {
                for (int i = 0; i < 3; i++) {
                    r.lock();
                    try {
                        System.out.println("任务2");
                        map.get("x");
                    } finally {
//                        r.unlock();
                    }
                }
            }
        };


        Thread thread = new Thread(runnable, "线程1");
        thread.start();

        Thread thread2 = new Thread(runnable2, "线程2");
        thread2.start();


//        Thread thread2 = new Thread(runnable, "线程2");
//        thread.start();


    }


}
