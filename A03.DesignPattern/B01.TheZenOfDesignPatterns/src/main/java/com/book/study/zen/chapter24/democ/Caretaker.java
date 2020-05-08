package com.book.study.zen.chapter24.democ;

import com.book.study.zen.chapter24.demob.Memento;

public class Caretaker {

    // 备忘录对象
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

}
