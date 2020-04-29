package com.book.study.zen.chapter15.democ;

public class Invoker {

    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void action(){
        this.command.execute();
    }

}
