package com.book.study.zen.chapter13.demod;

public class Client {

    public static void main(String[] args) {
        // 产生一个对象
        Thing thing = new Thing();
        // 设置一个值
        thing.setValue("张三");
        // 拷贝一个对象
        Thing cloneThing = thing.clone();
        cloneThing.setValue("李四");


        System.out.println(thing.getValue());
        System.out.println(cloneThing.getValue());
        // 1580066828
        System.out.println(thing.hashCode());
        // 491044090
        System.out.println(cloneThing.hashCode());
    }
}
