package com.book.study.zen.chapter12.demoa;

import com.book.study.zen.chapter12.IGamePlayer;

public class Client {

    public static void main(String[] args) {

        // 自己玩
//        playByMyself();

        // 代练
        playByProxy();

    }

    private static void playByProxy() {
        // 定义一个痴迷的玩家
        IGamePlayer player = new GamePlayer("张三");

        // 定义代练
        IGamePlayer proxy = new GamePlayerProxy(player);

        System.out.println("开始时间：2020-04-27 08:30:30");
        proxy.login("zhangsan", "password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        //记录结束游戏时间
        System.out.println("结束时间时：2020-04-27 11:30:30");


    }

    private static void playByMyself() {
        // 定义一个痴迷的玩家
        IGamePlayer player = new GamePlayer("张三");

        System.out.println("开始时间：2020-04-27 08:30:30");
        player.login("zhangsan", "password");
        // 开始杀怪
        player.killBoss();
        // 升级
        player.upgrade();
        //记录结束游戏时间
        System.out.println("结束时间时：2020-04-27 11:30:30");


    }

}
