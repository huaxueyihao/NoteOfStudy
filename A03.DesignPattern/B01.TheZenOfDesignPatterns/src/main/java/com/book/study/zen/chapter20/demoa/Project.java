package com.book.study.zen.chapter20.demoa;

import com.book.study.zen.chapter11.demob.Product;

public class Project implements IProject {

    // 项目名称
    private String name = "";
    // 项目成员数量
    private int num = 0;
    // 项目费用
    private int cost = 0;

    public Project(String name, int num, int cost) {
        // 赋值到类的成员变量中
        this.name = name;
        this.num = num;
        this.cost = cost;
    }

    @Override
    public String getProjectInfo() {
        String info = "";
        // 获得项目的名称
        info = info + "项目名称是：" + this.name;
        // 获得项目人数
        info = info + "\t项目人数：" + this.num;
        // 项目费用
        info = info + "\t 项目费用：" + this.cost;
        return info;
    }
}
