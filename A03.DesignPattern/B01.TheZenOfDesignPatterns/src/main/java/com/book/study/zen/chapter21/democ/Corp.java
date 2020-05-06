package com.book.study.zen.chapter21.democ;

public class Corp {

    // 公司每个人都有名称
    private String name = "";
    // 公司每个人都职位
    private String position = "";
    // 公司每个人都有薪水
    private int salary = 0;

    public Corp(String name, String position, int salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // 获得员工信息
    public String getInfo() {
        String info = "";
        info = "名称：" + this.name;
        info = info + "\t职位:" + this.position;
        info = info + "\t薪水:" + this.salary;
        return info;
    }
}
