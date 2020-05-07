package com.book.study.zen.chapter22.democ;

import com.book.study.zen.chapter22.demoa.IHanFeiZi;

import java.util.ArrayList;

public class HanFeiZi implements Observable, IHanFeiZi {

    //定义个变长数组，存放所有的观察者
    private ArrayList<Observer> observerList = new ArrayList<>();

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:开始吃饭了...");
        // 通知所有的观察者
        this.notifyObservers("韩非子在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        this.notifyObservers("韩非子在娱乐");
    }

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers(String context) {
        for (Observer observer : observerList) {
            observer.update(context);
        }
    }
}
