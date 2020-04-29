package com.book.study.zen.chapter15.demoa;

public abstract class Group {

    // 甲乙双发分开办公，如果你要和某个组讨论，你首先要到这个组
    public abstract void find();

    // 被要求增加删除功能
    public abstract void add();

    // 被要求删除功能
    public abstract void delete();

    // 被要求删除功能
    public abstract void change();

    // 被要求给出游的变更计划
    public abstract void plan();

}
