package com.book.study.zen.chapter17.pattern;

public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void operate() {
        super.operate();
        this.method2();
    }

    private void method2() {
        System.out.println("method2 修饰");
    }
}
