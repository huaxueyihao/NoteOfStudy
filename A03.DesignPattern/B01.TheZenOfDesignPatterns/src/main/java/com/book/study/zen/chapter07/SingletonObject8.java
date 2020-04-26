package com.book.study.zen.chapter07;

/**
 * 枚举实现单例模式
 */
public enum SingletonObject8 {

    INSTANCE,
    ;

    private final DBConnection instance;

    private SingletonObject8() {
        instance = new DBConnection();

    }

    public DBConnection getInstance() {
        return instance;
    }


}
