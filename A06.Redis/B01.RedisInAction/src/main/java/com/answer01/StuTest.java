package com.answer01;

import java.util.*;

public class StuTest {

    public static void main(String[] args) {

        testAdd();

    }

    /**
     * ������ӵķ���
     */
    private static void testAdd() {

        StuArrayListImpl stuArrayList = new StuArrayListImpl();
        Student student = new Student("����", "1", "man", 20);
        stuArrayList.add(student);
        student = new Student("��4", "2", "man", 22);
        stuArrayList.add(student);


        // ����ǶԵ����������������2
        System.out.println(stuArrayList.size());
        // ����ֱ��toString�������,�������ֱ�ӿ�����ӽ��
        System.out.println(stuArrayList.toString());

    }


}

