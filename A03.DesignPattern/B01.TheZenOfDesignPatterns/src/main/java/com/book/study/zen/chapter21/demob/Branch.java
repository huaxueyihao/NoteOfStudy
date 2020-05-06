package com.book.study.zen.chapter21.demob;

import java.util.ArrayList;

public class Branch implements IBranch {

    // 领导也是人，也有名字
    private String name;

    // 领导和领导不同，也是职位区别
    private String position = "";

    // 领导也是拿薪水的
    private int salary = 0;

    // 领导下边有哪些领导和小兵
    ArrayList<ICorp> subordinateList = new ArrayList<>();

    // 通过构造函数传递领导的信息
    public Branch(String name, String position, int salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public void addSubordinate(ICorp corp) {
        this.subordinateList.add(corp);
    }

    @Override
    public ArrayList<ICorp> getSubordinate() {
        return this.subordinateList;
    }

    @Override
    public String getInfo() {
        String info = "";
        info = "名称：" + this.name;
        info = info +"\t职位:"+this.position;
        info = info + "\t薪水:"+this.salary;
        return info;
    }
}
