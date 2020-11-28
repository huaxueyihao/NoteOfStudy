package com.answer01;

import java.util.*;

public class StuTest {

    public static void main(String[] args) {

        testAdd();

    }

    /**
     * 测试添加的方法
     */
    private static void testAdd() {

        StuArrayListImpl stuArrayList = new StuArrayListImpl();
        Student student = new Student("张三", "1", "man", 20);
        stuArrayList.add(student);
        student = new Student("李4", "2", "man", 22);
        stuArrayList.add(student);


        // 如果是对的至少这里输出的是2
        System.out.println(stuArrayList.size());
        // 或者直接toString方法输出,这里可以直接看到添加结果
        System.out.println(stuArrayList.toString());

    }


}

