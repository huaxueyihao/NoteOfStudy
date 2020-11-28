package com.book.study.zen.chapter18.demoa;

public class GivenGreenLight implements IStrategy {

    @Override
    public void operate() {
        System.out.println("求吴国太开绿灯，放行！");
    }

}
