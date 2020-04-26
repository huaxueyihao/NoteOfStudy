package com.book.study.zen.chapter08;

public abstract class AbstractHumanFactory {

    public abstract <T extends Human> T createHuman(Class<T> c);


}
