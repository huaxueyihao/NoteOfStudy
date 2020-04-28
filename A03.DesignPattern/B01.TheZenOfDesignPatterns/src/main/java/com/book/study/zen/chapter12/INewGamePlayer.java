package com.book.study.zen.chapter12;

public interface INewGamePlayer {

    // 登陆游戏
    public void login(String user, String password);

    // 杀怪，网络游戏的主要特色
    public void killBoss();

    // 升级
    public void upgrade();


    public INewGamePlayer getProxy();

}
