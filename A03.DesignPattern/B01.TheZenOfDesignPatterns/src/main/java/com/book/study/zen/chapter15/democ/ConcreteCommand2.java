package com.book.study.zen.chapter15.democ;

public class ConcreteCommand2 extends Command {

    // 哪个Receiver类进行命令处理
    private Receiver receiver;

    public ConcreteCommand2(Receiver receiver){
        this.receiver = receiver;
    }

    // 构造函数传递接收者
    @Override
    public void execute() {
        this.receiver.doSomething();
    }
}
