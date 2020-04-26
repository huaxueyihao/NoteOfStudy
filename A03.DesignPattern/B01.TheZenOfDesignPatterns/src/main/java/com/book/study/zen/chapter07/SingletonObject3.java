package com.book.study.zen.chapter07;

/**
 * 懒汉式:方法加同步，解决多线程问题
 */
public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3(){
    }

    public synchronized static SingletonObject3 getInstance(){
        if(null == instance){
            instance = new SingletonObject3();
        }
        return SingletonObject3.instance;
    }


}
