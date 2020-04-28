package com.book.study.zen.chapter12.demod;

import com.book.study.zen.chapter12.INewGamePlayer;

public class Client {

    public static void main(String[] args) {

        // 自己玩
//        playByMySelf();

        //随便找一个代练
//        playByProxyWithoutPurpose();

        // 指定代练
        playByProxyWhithPurpose();

    }

    private static void playByProxyWhithPurpose() {
        // 定义一个游戏角色
        INewGamePlayer player = new GamePlayer("张三");
        // 自己找一个代练
        INewGamePlayer proxy = player.getProxy();
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是：2020-04-27 08:00:00");
        proxy.login("zhangsan", "password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        // 记录结束游戏时间
        System.out.println("结束时间是：2020-04-27 14:00:00");
    }

    private static void playByProxyWithoutPurpose() {
        // 定义一个游戏角色
        INewGamePlayer player = new GamePlayer("张三");
        // 自己找一个代练
        INewGamePlayer proxy = new GamePlayerProxy(player);
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是：2020-04-27 08:00:00");
        proxy.login("zhangsan", "password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        // 记录结束游戏时间
        System.out.println("结束时间是：2020-04-27 14:00:00");

    }

    private static void playByMySelf() {
        // 定义一个游戏角色
        INewGamePlayer player = new GamePlayer("张三");
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是：2020-04-27 08:00:00");
        player.login("zhangsan", "password");
        // 开始杀怪
        player.killBoss();
        // 升级
        player.upgrade();
        // 记录结束游戏时间
        System.out.println("结束时间是：2020-04-27 14:00:00");

    }

}
