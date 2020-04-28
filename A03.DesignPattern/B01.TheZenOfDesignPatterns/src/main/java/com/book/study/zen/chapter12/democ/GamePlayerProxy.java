package com.book.study.zen.chapter12.democ;

import com.book.study.zen.chapter12.IGamePlayer;

public class GamePlayerProxy implements IGamePlayer {

    private IGamePlayer gamePlayer = null;

    // 通过构造函数传递要对谁进行代练

    public GamePlayerProxy(String name) {
        try {
            this.gamePlayer = new GamePlayer(this,name);
        }catch (Exception e){
            // TODO 异常处理
        }

    }

    @Override
    public void login(String user, String password) {
        this.gamePlayer.login(user,password);
    }

    // 通过构造函数传递要对谁进行代练
    @Override
    public void killBoss() {
        this.gamePlayer.killBoss();
    }

    // 通过构造函数传递要对谁进行代练
    @Override
    public void upgrade() {
        this.gamePlayer.upgrade();
    }
}
