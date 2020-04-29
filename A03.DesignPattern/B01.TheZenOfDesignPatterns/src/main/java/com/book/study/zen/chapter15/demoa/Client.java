package com.book.study.zen.chapter15.demoa;

public class Client {

    public static void main(String[] args) {

        // 首先客户找到需求组说，过来谈需求，并修改
        System.out.println("----------客户要求增加一项需求---------");
        Group rg = new RequirementGroup();
        rg.find();
        rg.add();
        rg.plan();

    }


}
