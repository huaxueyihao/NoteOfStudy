package com.book.study.zen.chapter28.demob;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FlyweightFactory {

    // 定义一个池容器
    private static HashMap<String, Flyweight> pool = new HashMap<>();

    // 享元工厂
    public static Flyweight getFlyweight(String Extinsic) {
        // 需要返回的对象
        Flyweight flyweight = null;
        if (pool.containsKey(Extinsic)) {
            flyweight = pool.get(Extinsic);
        } else {
            // 根据外部状态创建享元对象
            flyweight = new ConcreteFlyweight1(Extinsic);
            // 放置到池中
            pool.put(Extinsic,flyweight);
        }
        return flyweight;
    }


}
