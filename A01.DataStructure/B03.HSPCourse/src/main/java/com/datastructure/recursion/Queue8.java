package com.datastructure.recursion;

import java.util.Queue;

public class Queue8 {

    int max = 8;

    int[] array = new int[max];

    static int count = 0;
    public static void main(String[] args) {

        Queue8 queue8 = new Queue8();
        queue8.check(0);

        System.out.println(Queue8.count);

    }

    // 方置第n个皇后
    private void check(int n){
        if(n == max){ // n = 8
            print();
            return;
        }

        // 一次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前这个换后n，放到改行的第一列
            array[n] = i;
            if(judge(n)){
                //不冲突，方n+1
                check(n+1);
            }
        }
    }

    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 1. array[i] == array[n]  表示判断 第n个皇后是否和前面的n-1个换后在同一列
            // 2. Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第1往后是否在同一斜线
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    // 写一个方法，可以将结果输出
    private void print() {
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}
