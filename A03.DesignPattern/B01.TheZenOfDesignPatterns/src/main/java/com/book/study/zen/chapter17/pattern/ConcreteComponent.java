package com.book.study.zen.chapter17.pattern;

import com.book.study.zen.chapter17.pattern.Component;

public class ConcreteComponent extends Component {
    @Override
    public void operate() {
        System.out.println("doSomething");
    }



}
