package com.book.study.zen.chapter29.demoa;

public class ClothesCorp extends Corp {


    @Override
    protected void produce() {
        System.out.println("服装公司生产衣服...");
    }

    @Override
    protected void sell() {
        System.out.println("服务装公司出售衣服...");
    }


    // 服装公司不景气，但怎么说也是赚钱行业
    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("服装公司赚小钱...");
    }
}
