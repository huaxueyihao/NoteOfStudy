package com.datastructure.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        CircleArrayQueue arrayQueue = new CircleArrayQueue(3);

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

class CircleArrayQueue {

    private int maxSize;// 数组最大值

    // 指向对列的第一个元素，也就是arr[front]
    private int front; // 头

    // 指向对列的最后一个元素的后一个位置，希望空出一个位置
    private int rear; // 尾

    private int[] arr;

    // 创建对列
    public CircleArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    // 判断对列满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        arr[rear] = n;
        // 将rear后移，必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    // 获取对列的数据
    public int getQueue() {
        // 判断对列是否空
        if (isEmpty()) {
            throw new RuntimeException("对列为空");
        }
        // front是指向对列的第一个元素
        // 1. 先把front对应的值保留到一个临时变量
        // 2. 将front后移，考虑取模
        // 3. 将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % front;
        return value;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("对列空");
            return;
        }
        // 从front开始遍历，遍历多少个元素
        //

        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出当前对列有效数据的个数
    public int size() {
        //
        return (rear + maxSize - front) % maxSize;
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("对列为空");
        }
        return arr[front + 1];
    }
}

