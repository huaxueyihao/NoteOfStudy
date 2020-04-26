package com.book.study.zen.chapter07;

import java.lang.reflect.Constructor;

public class MainClass {


    public static void main(String[] args) throws Exception {

        DBConnection s = SingletonObject8.INSTANCE.getInstance();


        Constructor<? extends SingletonObject8> constructor = SingletonObject8.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        SingletonObject8 s2 = constructor.newInstance();

        System.out.println(s);
        System.out.println(s2);


    }
}
