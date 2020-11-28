package com.answer01;

/**
 * 提供对学生数组管理的行为操作
 */
public class StuManager {

    Student[] studentArray = new Student[100];

    /**
     * 添加学生
     *
     * @param student 学生对象
     */
    public void add(Student student) {
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] == null) {
                studentArray[i] = student;
                break;
            }
        }
    }

    /**
     * 根据学生姓名删除学生
     *
     * @param name 学生姓名
     */
    public Student remove(String name) {
        Student temp = null;
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] != null && studentArray[i].getName().equals(name)) {
                temp = studentArray[i];
                studentArray[i] = null;
                break;
            }
        }
        return temp;
    }


    /**
     * 根据学生姓名查找学生
     *
     * @param name 学生姓名
     */
    public Student getbyName(String name) {
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] != null && studentArray[i].getName().equals(name)) {
                return studentArray[i];
            }
        }
        return null;
    }

    //	展示所有学生
    public void showAll() {
        for (int i = 0; i < studentArray.length; i++) {
            Student student = studentArray[i];
            if (student != null) {
                System.out.println("学号：" + student.getId() + "姓名：" + student.getName() + "年龄：" + student.getAge() + "性别：" + student.getSex());
            }
        }
    }


}
