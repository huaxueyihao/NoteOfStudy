package com.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {

    public static void main(String[] args) {

        //将一个中缀表达式转成后缀表达式的功能
        // 1. 1 + ((2+3)*4)-5 => 1 2 3 + 4 * + 5 -
        // 2.
        String infixExpression = "1+((2+3)*4)-5";

        List<String> list = toInfixExpressionList(infixExpression);

        List<String> resList = parseSuffixExpressionList(list);
        System.out.println(resList);


        // 先定义逆波兰表达式
//        String suffixExpression = "3 4 + 5 * 6 -";

        // 思路
        // 1.现将 "3 4 + 5 * 6 - " => 放到ArrayList中
        // 2.将ArrayList传递给一个方法，遍历配合栈 完成计算
        //


//        List<String> list = getListString(suffixExpression);
//        System.out.println(list);
//
        int res = calculate(resList);
        System.out.println(res);

    }

    // 将得到的中缀表达式对应的List=>后缀表达式对应的List
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        // 定义两个栈
        Stack<String> s1 = new Stack<>();//符号
        List<String> s2 = new ArrayList<>();//储存

        for (String item : ls) {
            // 如果是一个数，加入s2
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);

            } else if (item.equals(")")) {
                // 依次弹出s1栈顶的运算符，并压入s2，知道遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();
            } else {
                // 当item的优先级小于等于栈顶的元运算符，将s1栈顶的运算符弹出加入到s2中，再次转到(4,1)与s1中新的栈顶运算符相比较
                //
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                // 还需要将item压入栈
                s1.push(item);

            }
        }

        //
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }


    // 将中缀表达式转成对应的List
    public static List<String> toInfixExpressionList(String s) {
        List<String> ls = new ArrayList<>();
        int i = 0; // 一个指针，用于遍历字符串
        String str;
        char c;

        do {
            // 如果c是一个非数字，需要加入到ls中
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else { // 需要靠考虑多位数
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                ls.add(str);
            }

        } while (i < s.length());

        return ls;

    }


    //将一个逆波兰表达式，一次将数据和运算符方法到ArrayList中

    public static List<String> getListString(String suffixExpression) {
        // 将suffixExpression分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split) {
            list.add(ele);
        }

        return list;
    }


    public static int calculate(List<String> ls) {
        // 创建栈，
        Stack<String> stack = new Stack<>();


        for (String item : ls) {
            if (item.matches("\\d+")) {
                // 直接入栈
                stack.push(item);
            } else {
                // pop出两个数，并运算
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());

                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;

                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                // 结果入栈
                stack.push(res + "");
            }


        }

        // 最后留在stack中的数据就是运算结果
        String pop = stack.pop();
        return Integer.parseInt(pop);
    }

}

// 可以返回一个运算符对应的优先级
class Operation {

    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    // 返回优先级
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }

}
