package com.datastructure.sort;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int arr[] = {3, 9, -1, 10, -2};

//        int temp = 0;
//        for (int j = 0; j < arr.length - 1; j++) {
//            if (arr[j] > arr[j + 1]) {
//                temp = arr[j];
//                arr[j] = arr[j + 1];
//                arr[j + 1] = temp;
//            }
//        }
//
//        System.out.println("第一趟排序后的数组");
//        System.out.println(Arrays.toString(arr));
//
//        for (int j = 0; j < arr.length - 2; j++) {
//            if (arr[j] > arr[j + 1]) {
//                temp = arr[j];
//                arr[j] = arr[j + 1];
//                arr[j + 1] = temp;
//            }
//        }
//
//
//        System.out.println("第二趟排序后的数组");
//        System.out.println(Arrays.toString(arr));
//
//        for (int j = 0; j < arr.length - 3; j++) {
//            if (arr[j] > arr[j + 1]) {
//                temp = arr[j];
//                arr[j] = arr[j + 1];
//                arr[j + 1] = temp;
//            }
//        }
//
//
//        System.out.println("第三趟排序后的数组");
//        System.out.println(Arrays.toString(arr));
//
//        for (int j = 0; j < arr.length - 4; j++) {
//            if (arr[j] > arr[j + 1]) {
//                temp = arr[j];
//                arr[j] = arr[j + 1];
//                arr[j + 1] = temp;
//            }
//        }
//
//
//        System.out.println("第四趟排序后的数组");
//        System.out.println(Arrays.toString(arr));

        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!flag) {
                break;
            } else {
                // 重置flag
                flag = false;
            }
        }
    }


}
