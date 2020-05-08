package com.book.study.zen.chapter29.demob;

public class IPod extends Product {


    @Override
    public void beProducted() {
        System.out.println("生产出的ipod是这样的...");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的ipod是卖出去了...");
    }
}
