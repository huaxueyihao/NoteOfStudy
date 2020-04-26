package com.book.study.zen.chapter07;


/**
 * 静态内部类的方式：当访问SingletonObject6的类时，会先初始化InstanceHolder，于是达到了访问的时候才初始化，而且由于是静态内部类实现的
 * 所以，
 */
public class SingletonObject6 {

    /**
     * 1.类装载
     * 2.初始化
     */
    private SingletonObject6(){

    }

    private static class InstanceHolder{
        private final static SingletonObject6 intance = new SingletonObject6();
    }

    public static SingletonObject6 getInstance(){
        return InstanceHolder.intance;
    }



}
