package com.datastructure.stack;

import java.util.Stack;

public class SingleLinkedStackDemo {

    public static void main(String[] args) {

        SingleLinkedStack stack = new SingleLinkedStack(5);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        stack.push(5);
        stack.push(6);


    }

}

class SingleLinkedStack {

    private StackNode head = new StackNode();

    private int maxSize;

    private int top;

    public SingleLinkedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        return top == maxSize;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if (isFull()) {
            System.out.println("满了");
            return;
        }

        StackNode next = head.next;

        StackNode stackNode = new StackNode();
        stackNode.value = value;
        head.next = stackNode;
        stackNode.next = next;
        top++;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空了");
        }

        StackNode topStack = head.next;
        head.next = topStack.next;
        topStack.next = null;
        top--;
        return topStack.value;
    }


    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }

        StackNode temp = head.next;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }

    }


}

class StackNode {

    int value;

    StackNode next;


    @Override
    public String toString() {
        return "StackNode{" +
                "value=" + value +
                '}';
    }
}
