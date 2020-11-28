package com.answer01;

/**
 * 管理学生的接口
 */
public interface StuInterface {

    /**
     * 添加一个学生
     *
     * @param student
     */
    void add(Student student);

    /**
     * 展示全部的学生：直接输出就行了
     */
    void showAll();

    /**
     * 根据学生姓名获得某个学生对象
     *
     * @param name
     * @return
     */
    Student getByName(String name);


    /**
     * 根据姓名删除学生，返回删除的学生
     *
     * @param name
     * @return
     */
    Student remove(String name);


    /**
     * 返回，当前有多少个学生
     *
     * @return
     */
    int size();


}
