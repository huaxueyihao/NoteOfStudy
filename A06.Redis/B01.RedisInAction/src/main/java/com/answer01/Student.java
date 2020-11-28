package com.answer01;

//import java.util.Scanner;

//import java.util.Scanner;
public class Student {
    private String name;
    private String id;
    private String sex;
    private int age;

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex() {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age = age;
    }

    public Student() {
    }

    public Student(String name, String id, String sex, int age) {
        super();
        this.name = name;
        this.id = id;
        this.sex = sex;
        this.age = age;
    }

}
