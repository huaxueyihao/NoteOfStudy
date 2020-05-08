package com.book.study.zen.chapter24.democ;

import com.book.study.zen.chapter24.demob.Boy;

public class Client {

    public static void main(String[] args) {
        // 声明处主角
        Boy boy = new Boy();
        // 声明出被忘录的管理者
        Caretaker caretaker = new Caretaker();
        // 初始化当前状态
        boy.setState("心情很棒");
        System.out.println("=======男孩现在的状态=========");
        System.out.println(boy.getState());

        // 需要记录下当前状态呀
        caretaker.setMemento(boy.createMemento());
        // 男孩去追女孩，状态改变
        boy.changeState();
        System.out.println("\n=========男孩追女孩子后的状态==========");
        System.out.println(boy.getState());

        // 追女孩子失败，恢复原状
        boy.restoreMemento(caretaker.getMemento());
        System.out.println("\n=========男孩恢复后的状态==========");
        System.out.println(boy.getState());



    }
}
