package com.book.study.zen.chapter15.demob;

public class Invoker {

    // 什么命令
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    // 执行客户的命令
    public void action(){
        this.command.execute();
    }


}
