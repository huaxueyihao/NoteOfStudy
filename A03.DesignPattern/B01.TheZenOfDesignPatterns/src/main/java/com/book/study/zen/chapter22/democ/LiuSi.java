package com.book.study.zen.chapter22.democ;

public class LiuSi implements Observer {

    // 王斯，看到韩非子有活动
    @Override
    public void update(String context) {
        System.out.println("刘斯:观察到韩非子活动，开始动作了...");
        this.cry(context);
        System.out.println("刘斯:乐死了...\n");
    }

    private void cry(String context) {
        System.out.println("刘斯:因为" + context + ",--所以我快乐呀!");
    }
}
