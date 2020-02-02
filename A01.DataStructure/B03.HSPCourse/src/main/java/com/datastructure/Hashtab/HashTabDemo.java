package com.datastructure.Hashtab;

import java.util.Scanner;

public class HashTabDemo {


    public static void main(String[] args) {

        // 创建哈希表
        HashTab hashTab = new HashTab(7);

        // 写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("add：添加雇员");
            System.out.println("list：显示雇员");
            System.out.println("exit：退出系统");


            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入Id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();

                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("输入Id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;

            }
        }

    }


}

class HashTab {
    private EmpLinkedList[] empLinkedListArray;

    private int size;


    public HashTab(int size) {
        // 初始化empLinkedListArray
        this.size = size;
        empLinkedListArray = new EmpLinkedList[size];

        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }

    }

    // 添加雇员

    public void add(Emp emp) {
        // 根据员工id，得到该员工应当添加到哪条链表

        int empLinkedListNo = hashFun(emp.id);
        // 将emp，添加到对应链表中
        empLinkedListArray[empLinkedListNo].add(emp);

    }

    public void list() {

        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i + 1);
        }

    }


    public void findEmpById(int id) {
        // 使用三联函数确定到哪条链表查找
        int empLinkedListNo = hashFun(id);
        Emp emp = empLinkedListArray[empLinkedListNo].findEmpById(id);
        if (emp != null) {
            System.out.printf("在第%d条链表中找到雇员id = %d\n", empLinkedListNo + 1, id);
        } else {
            System.out.println("在hash表中没有找到");
        }

    }


    public int hashFun(int id) {
        return id % size;
    }


}

class Emp {

    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }


}

class EmpLinkedList {
    // 头指针，执行第一个Emp，因此我们这个链表的head，是直接指向第一个Emp

    private Emp head;

    // 添加雇员链表
    // 1.假定，当添加雇员时，id是自增长，即id的分配总是从小到大的
    public void add(Emp emp) {
        // 如果是添加第一个雇员
        if (head == null) {
            head = emp;
            return;
        }

        // 如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
        Emp curEmp = head;
        while (true) {
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }

        curEmp.next = emp;

    }

    // 遍历链表的雇员信息
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + no + " 链表空的");
            return;
        }

        System.out.println("第" + no + " 链表信息为：");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=> id=%d name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }

        System.out.println();
    }

    // 根据id查找雇员
    public Emp findEmpById(int id) {
        // 判断空
        if (head == null) {
            System.out.println("链表空");
            return null;
        }

        Emp cutEmp = head;
        while (true) {
            if (cutEmp.id == id) {// 找到
                break;
            }
            if (cutEmp.next == null) {// 空了
                cutEmp = null;
                break;
            }
            cutEmp = cutEmp.next;
        }
        return cutEmp;
    }

}
