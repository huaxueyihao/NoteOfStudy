package com.book.study.zen.chapter12.democ;

import com.book.study.zen.chapter12.IGamePlayer;

public class Client {

    public static void main(String[] args) {
        // 然后再定义一个代练者
        IGamePlayer proxy = new GamePlayerProxy("张三");
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是：2020-04-27 08:00:00");

        proxy.login("zhangsan","password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        System.out.println("结束时间是：2020-04-27 14:00:00");
    }
}
