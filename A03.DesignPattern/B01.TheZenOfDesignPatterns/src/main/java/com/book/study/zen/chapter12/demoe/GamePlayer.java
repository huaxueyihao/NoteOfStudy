package com.book.study.zen.chapter12.demoe;

import com.book.study.zen.chapter12.IGamePlayer;

public class GamePlayer implements IGamePlayer {

    private String name = "";


    // 构造函数限制谁能创建对象，并同时传递姓名
    public GamePlayer(String _name) {
        this.name = _name;
    }


    // 进游戏之前你肯定要登陆吧，这是一个必要条件
    @Override
    public void login(String user, String password) {
        System.out.println("登陆名为" + user + "的用户" + this.name + "登陆成功!");
    }

    // 打怪，最期望的就是杀老怪
    @Override
    public void killBoss() {
        System.out.println(this.name + "在打怪");
    }

    // 升级，升级有很多方法，花钱买是一种，做任务也是一种
    @Override
    public void upgrade() {
        System.out.println(this.name + "又升了一级");
    }
}
