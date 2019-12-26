package com.book.study.zen.chapter17.pattern;

public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void operate() {
        this.method1();
        super.operate();
    }

    private void method1() {
        System.out.println("method1 修饰");
    }
}
