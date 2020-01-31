package com.datastructure.stack;

import java.util.Scanner;

public class ArrayStackDemo {

    public static void main(String[] args) {

        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop){
            System.out.println("show:表示显示栈");
            System.out.println("exit:退出程序");
            System.out.println("push:表示添加数据到栈(入栈)");
            System.out.println("pop:表示从栈取出数据");
            System.out.println("请你输入你的选者");

           key =  scanner.next();
           switch (key){
               case "show":
                   stack.list();
                   break;
               case "push":
                   System.out.println("请输入一个数");
                   int value = scanner.nextInt();
                   stack.push(value);
                   break;
               case "pop":
                   int pop = stack.pop();
                   System.out.println("出栈的数据是"+pop);
                   break;
               case "exit":
                   scanner.close();
                   loop = false;
                   break;
           }
        }

    }
}


class ArrayStack {

    private int maxSize; // 栈大小
    private int[] stack;
    private int top = -1;// 栈顶


    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public boolean isFull(){
        return top == maxSize;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public void push(int value){
        if(isFull()){
            System.out.println("满了");
            return;
        }

        top++;
        stack[top] = value;
    }

    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈空了");
        }

        int value = stack[top];
        top--;
        return value;
    }


    public void list(){
        if(isEmpty()){
            System.out.println("栈空，没有数据");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n",stack[i]);
        }

    }















}
