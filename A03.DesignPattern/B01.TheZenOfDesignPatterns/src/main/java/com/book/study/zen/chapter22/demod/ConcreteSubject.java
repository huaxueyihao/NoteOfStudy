package com.book.study.zen.chapter22.demod;

public class ConcreteSubject extends Subject {

    public void doSomething() {
        super.notifyObservers();
    }

}
