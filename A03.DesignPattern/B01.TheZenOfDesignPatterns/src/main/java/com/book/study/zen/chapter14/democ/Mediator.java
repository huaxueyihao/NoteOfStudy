package com.book.study.zen.chapter14.democ;

public abstract class Mediator {

    // 定义同事类
    protected ConcreteColleague1 c1;
    protected ConcreteColleague2 c2;

    // 中介者模式的业务逻辑
    public abstract void doSomething1();
    public abstract void doSomething2();



}
