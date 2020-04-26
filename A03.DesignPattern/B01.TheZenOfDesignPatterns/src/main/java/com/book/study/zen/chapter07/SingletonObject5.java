package com.book.study.zen.chapter07;

/**
 * 懒汉式：用volatile关键字解决double check问题
 */
public class SingletonObject5 {

    // 保证可见性、避免重排序
    private static volatile SingletonObject5 instance;

    private SingletonObject5() {

    }

    public static SingletonObject5 getInstance() {
        if (null == instance) {
            synchronized (SingletonObject5.class) {
                if (null == instance) {
                    instance = new SingletonObject5();
                }
            }
        }
        return SingletonObject5.instance;
    }


}
