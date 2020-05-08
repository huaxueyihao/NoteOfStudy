package com.book.study.zen.chapter24.demob;

public class Client {

    public static void main(String[] args) {
        // 声明出主角
        Boy boy = new Boy();
        // 初始化当前状态
        boy.setState("心情很棒!");
        System.out.println("========男孩现在的状态========");
        System.out.println(boy.getState());

        Memento mem = boy.createMemento();
        boy.changeState();
        System.out.println("\n==========男孩子追女孩子后的状态===========");
        System.out.println(boy.getState());
        // 追女孩子失败，恢复原状
        boy.restoreMemento(mem);
        System.out.println("\n===========男孩子恢复后的状态============");
        System.out.println(boy.getState());


    }


}
