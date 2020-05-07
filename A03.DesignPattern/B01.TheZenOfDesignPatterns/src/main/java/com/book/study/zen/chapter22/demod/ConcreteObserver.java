package com.book.study.zen.chapter22.demod;

public class ConcreteObserver implements Observer{


    @Override
    public void update() {
        System.out.println("接收到信息，并行进行处理");
    }
}
