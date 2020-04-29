package com.book.study.zen.chapter15.democ;

public class ConcreteCommand1 extends Command {


    // 对哪个Receiver类进行命令处理
    private Receiver receiver;

    // 构造函数传递接收者
    public ConcreteCommand1(Receiver receiver){
        this.receiver = receiver;
    }


    @Override
    public void execute() {
        this.receiver.doSomething();
    }


}
