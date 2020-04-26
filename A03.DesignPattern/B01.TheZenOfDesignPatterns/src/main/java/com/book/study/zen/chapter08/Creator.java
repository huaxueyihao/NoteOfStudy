package com.book.study.zen.chapter08;

public abstract class Creator {

    public abstract <T extends Product> T createProduct(Class<T> c);

}
