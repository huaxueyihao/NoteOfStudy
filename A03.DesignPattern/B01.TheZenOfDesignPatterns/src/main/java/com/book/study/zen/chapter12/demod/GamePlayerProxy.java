package com.book.study.zen.chapter12.demod;


import com.book.study.zen.chapter12.INewGamePlayer;

public class GamePlayerProxy implements INewGamePlayer {


    private INewGamePlayer gamePlayer = null;

    public GamePlayerProxy(INewGamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @Override
    public void login(String user, String password) {
        this.gamePlayer.login(user,password);
    }

    @Override
    public void killBoss() {
        this.gamePlayer.killBoss();
    }

    @Override
    public void upgrade() {
        this.gamePlayer.upgrade();
    }

    @Override
    public INewGamePlayer getProxy() {
        return this;
    }
}
