package com.datastructure.stack;

public class Calculator {

    public static void main(String[] args) {

        String expression = "30+20*6-2";
        // 创建连个栈

        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);


        int index = 0;
        int num1 = 0;
        int num2 = 0;

        int oper = 0;
        int res = 0;
        char ch = ' ';

        String keepNum = "";

        while (true) {
            // 一次得到expression的每个字符。
            ch = expression.charAt(index);

            if (operStack.isOper(ch)) {
                // 判断当前的复函栈是否为空
                if (!operStack.isEmpty()) {
                    // 比较优先级
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();

                        res = numStack.cal(num1, num2, operStack.pop());
                        // 把运算结果入栈
                        numStack.push(res);
                        // 然后将当前的操作符号栈
                        operStack.push(ch);
                    } else {
                        // 如果当前的操作符的优先级栈中的操作符，就直接入符号栈
                        operStack.push(ch);
                    }
                } else {
                    // 如果为空
                    operStack.push(ch);
                }

            } else {// 如果是数，则直接入数栈
                // 多位数
                keepNum +=ch;
                if(index == expression.length() -1){
                    numStack.push(Integer.parseInt(keepNum));
                }else{
                    char next = expression.charAt(index + 1);
                    if(operStack.isOper(next)){
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";
                    }
                }


            }

            index++;
            if (index >= expression.length()) {
                break;
            }

        }


        while (true) {
            //如果符号栈为空，则计算到最后的结果，数栈中只有一个数字
            if (operStack.isEmpty()) {
                break;
            }

            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();

            res = numStack.cal(num1, num2, oper);
            numStack.push(res);


        }

        System.out.printf("表达式%s = %d", expression, numStack.pop());


    }

}


class ArrayStack2 {

    private int maxSize; // 栈大小
    private int[] stack;
    private int top = -1;// 栈顶


    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
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

        top++;
        stack[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空了");
        }

        int value = stack[top];
        top--;
        return value;
    }


    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", stack[i]);
        }

    }


    // 返回与哪算符的优先级，优先级是程序员来确定，优先级使用数字表示
    // 数字越大，则优先级就越高
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    // 判断是不是一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;

            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
        }
        return res;
    }


    public int peek() {
        return stack[top];
    }
}
