package com.book.study.zen.chapter14.democ;

public class ConcreteColleague2 extends Colleague {

    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    // 自有方法self-method
    public void selfMethod2() {
        // 处理自己的业务逻辑
    }

    // 自有方法dep-method
    public void depMethod2() {
        // 处理自己的业务逻辑
        // 自己不能处理的业务逻辑，委托给中介者处理
        super.mediator.doSomething1();
    }
}
