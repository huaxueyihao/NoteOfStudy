package com.book.study.zen.chapter07;

/**
 * 饿汉式
 */
public class SingletonObject1 {

    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1() {
    }

    public static SingletonObject1 getInstance() {
        return instance;
    }

}
