package com.book.study.zen.chapter17.pattern;

public abstract class Decorator extends Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    // 委托给被修饰这执行
    @Override
    public void operate() {
        this.component.operate();
    }
}
