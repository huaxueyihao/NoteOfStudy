package com.book.study.zen.chapter12.demod;

import com.book.study.zen.chapter12.INewGamePlayer;

public class GamePlayer implements INewGamePlayer {

    private String name = "";

    private INewGamePlayer proxy = null;

    public GamePlayer(String name) {
        this.name = name;
    }

    @Override
    public void login(String user, String password) {
        if (this.isProxy()) {
            System.out.println("登陆名为" + user + "的用户" + this.name + "登陆成功!");

        } else {
            System.out.println("请使用指定的代理访问");
        }
    }

    private boolean isProxy() {
        return this.proxy != null;
    }

    @Override
    public void killBoss() {
        if (this.isProxy()) {
            System.out.println(this.name + "在打怪");
        } else {
            System.out.println("请使用指定的代理访问");
        }
    }

    @Override
    public void upgrade() {
        if (this.isProxy()) {
            System.out.println(this.name + " 又升了一级");
        } else {
            System.out.println("请使用指定的代理访问");
        }

    }

    @Override
    public INewGamePlayer getProxy() {
        this.proxy = new GamePlayerProxy(this);
        return this.proxy;
    }
}
