package com.datastructure.sort;

import java.util.Arrays;

public class InsertSort {


    public static void main(String[] args) {

        int[] arr = {101, 34, 119, 1, -1, 89};

        System.out.println(Arrays.toString(arr));
        insertSort(arr);

        System.out.println(Arrays.toString(arr));

    }

    public static void insertSort(int[] arr) {


        for (int i = 1; i < arr.length; i++) {
            int insertVal = arr[i];
            int insertIndex = i - 1;
//            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
//                arr[insertIndex + 1] = arr[insertIndex];
//                insertIndex--;
//
//            }

            for (int j = i - 1; j >= 0; j--) {
                if (insertVal < arr[j]) {
                    arr[j + 1] = arr[j];
                    insertIndex = j - 1;// insertIndex 插入到j的前面，所以就是insertIndex = j - 1
                }
            }
            arr[insertIndex + 1] = insertVal;
        }
    }


    public static void insertSortStep(int[] arr) {

        // 第1轮 {101,34,119,1} =>{34,101,119,1}

        // 定义待插入的数
        int insertVal = arr[1];
        int insertIndex = 1 - 1;

        // 给insertVal 找到插入的位置
        // 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
        // 2. insertVal < arr[insertIndex] 待插入的数，还没找到插入的位置
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {

            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;

        }

        // 当退出while循环时，说明插入的位置找到，insertIndex + 1
        arr[insertIndex + 1] = insertVal;
        System.out.println("第1轮");
        System.out.println(Arrays.toString(arr));


        // 第2轮
        insertVal = arr[2];
        insertIndex = 2 - 1;

        // 给insertVal 找到插入的位置
        // 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
        // 2. insertVal < arr[insertIndex] 待插入的数，还没找到插入的位置
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {

            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;

        }

        // 当退出while循环时，说明插入的位置找到，insertIndex + 1
        arr[insertIndex + 1] = insertVal;
        System.out.println("第2轮");
        System.out.println(Arrays.toString(arr));


        // 第3轮
        insertVal = arr[3];
        insertIndex = 3 - 1;

        // 给insertVal 找到插入的位置
        // 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
        // 2. insertVal < arr[insertIndex] 待插入的数，还没找到插入的位置
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {

            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;

        }

        // 当退出while循环时，说明插入的位置找到，insertIndex + 1
        arr[insertIndex + 1] = insertVal;
        System.out.println("第3轮");
        System.out.println(Arrays.toString(arr));


    }

}
