package com.book.study.zen.chapter16.democ;

public class Client {

    public static void main(String[] args) {
        // 声明所有的处理节点
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNext(handler2);
        handler2.setNext(handler3);

        Response response = handler1.handleMessage(new Request());


    }


}
