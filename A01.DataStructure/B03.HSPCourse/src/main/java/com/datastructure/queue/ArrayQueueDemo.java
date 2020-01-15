package com.datastructure.queue;

import java.util.Scanner;

public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);

        char key = ' ';
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        while (loop) {
            System.out.println("s(show):显示对列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到对列");
            System.out.println("g(get):从对列取出数据");
            System.out.println("h(head):查看对列头部数据");

            key = scanner.next().charAt(0);

            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("对列头数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    arrayQueue.showQueue();
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

// 使用数组模拟对列--编写一个ArrayQueue类

class ArrayQueue {

    private int maxSize;// 数组最大值

    //
    private int front; // 头

    private int rear; // 尾

    private int[] arr;

    // 创建对列
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        // 指向对列头部的前一个位置
        front = -1;
        rear = -1;
    }

    // 判断对列满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    // 判断对列空
    public boolean isEmpty() {
        return front == rear;
    }

    //添加数据到对列
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("对列满，不能添加");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    // 获取对列的数据
    public int getQueue() {
        // 判断对列是否空
        if (isEmpty()) {
            throw new RuntimeException("对列为空");
        }

        front++;// front后移动
        return arr[front];

    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("对列空");
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("对列为空");
        }
        return arr[front + 1];
    }


}
