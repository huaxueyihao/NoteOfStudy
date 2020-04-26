package com.book.study.zen.chapter07;

/**
 * 懒汉式:double check方式解决多线程问题，实际上还是解决不了
 */
public class SingletonObject4 {


    private static SingletonObject4 instance;

    private SingletonObject4() {

    }

    public static SingletonObject4 getInstance() {
        if (null == instance) {
            synchronized (SingletonObject4.class) {
                if (null == instance) {
                    instance = new SingletonObject4();
                }
            }
        }
        return SingletonObject4.instance;
    }

}
