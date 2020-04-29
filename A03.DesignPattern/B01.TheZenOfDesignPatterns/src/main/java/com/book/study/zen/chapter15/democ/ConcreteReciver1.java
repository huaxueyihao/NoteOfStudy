package com.book.study.zen.chapter15.democ;

public class ConcreteReciver1 extends Receiver {

    // 每个接收者都必须处理一定的业务逻辑
    @Override
    public void doSomething() {
        System.out.println("receiver1 doSomething");
    }
}
