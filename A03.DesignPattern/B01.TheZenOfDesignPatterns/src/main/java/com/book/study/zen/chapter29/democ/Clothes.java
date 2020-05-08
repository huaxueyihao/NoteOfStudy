package com.book.study.zen.chapter29.democ;

import com.book.study.zen.chapter29.demob.Product;

public class Clothes extends Product {

    @Override
    public void beProducted() {
        System.out.println("生产出的衣服是这样的...");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的衣服是卖出了的...");
    }



}
