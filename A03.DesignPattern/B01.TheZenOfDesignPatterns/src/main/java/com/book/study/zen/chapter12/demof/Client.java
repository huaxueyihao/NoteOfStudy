package com.book.study.zen.chapter12.demof;

import com.book.study.zen.chapter12.IGamePlayer;
import com.book.study.zen.chapter12.democ.GamePlayerProxy;
import com.book.study.zen.chapter12.demoe.GamePlayer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {

    public static void main(String[] args) {

        // 定义一个痴迷的玩家
        IGamePlayer player = new GamePlayer("张三");
        // 定义一个handler
        InvocationHandler handler = new GamePlayIH(player);

        // 开始打游戏，记下时间戳
        System.out.println("开始时间是：2020-04-27 08:00:00");
        ClassLoader cl = player.getClass().getClassLoader();


        //动态产生一个代理者
        IGamePlayer proxy =  (IGamePlayer) Proxy.newProxyInstance(cl, new Class[]{IGamePlayer.class}, handler);
        proxy.login("zhangsan","password");
        proxy.killBoss();
        proxy.upgrade();

        System.out.println("结束时间是：2020-04-27 14:00:00");
    }


}
