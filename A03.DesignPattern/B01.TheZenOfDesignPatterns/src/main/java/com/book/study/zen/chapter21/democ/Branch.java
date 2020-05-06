package com.book.study.zen.chapter21.democ;

import java.util.ArrayList;

public class Branch extends Corp {

    // 领导下边有哪些下级领导和小兵
    ArrayList<Corp> subordinateList = new ArrayList<>();

    // 构造函数式必须的
    public Branch(String name, String position, int salary) {
        super(name, position, salary);
    }

    // 增加一个下属，可能是小头目，也可能是小兵
    public void addSubordinate(Corp corp){
        this.subordinateList.add(corp);
    }

    // 我有哪些下属
    public ArrayList<Corp> getSubordinate(){
        return this.subordinateList;
    }

}
