package com.datastructure.sort;

import java.util.Arrays;

public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        shellMoveSort(arr);


    }


    // 移位法
    public static void shellMoveSort(int[] arr) {
        int index = 0;
        for (int gap = arr.length; gap > 0; gap /= 2) {
            // 从第gap个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
            index++;
            System.out.println("希尔排序第" + index + "轮排序=" + Arrays.toString(arr));

        }
    }

    // 交换法
    public static void shellSort(int[] arr) {
        int temp = 0;
        int index = 0;
        for (int gap = arr.length; gap > 0; gap /= 2) {
            // 应为第一轮排序，是将10个数据分成了5组
            for (int i = gap; i < arr.length; i++) {
                // 遍历各组中所有元素
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如归当前元素大于加上步长后的那个元素，说明交换
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            index++;
            System.out.println("希尔排序第" + index + "轮排序=" + Arrays.toString(arr));

        }
    }

    public static void shellSortStep(int[] arr) {

        // 希尔排序的第一轮排序

        int temp = 0;
        // 应为第一轮排序，是将10个数据分成了5组
        for (int i = 5; i < arr.length; i++) {
            // 遍历各组中所有元素
            for (int j = i - 5; j >= 0; j -= 5) {
                // 如归当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }

        System.out.println("第1轮");
        System.out.println(Arrays.toString(arr));


        // 应为第2轮排序，是将10个数据分成了5/2 = 2 组
        for (int i = 2; i < arr.length; i++) {
            // 遍历各组中所有元素
            for (int j = i - 2; j >= 0; j -= 2) {
                // 如归当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }

        System.out.println("第2轮");
        System.out.println(Arrays.toString(arr));

        // 应为第2轮排序，是将10个数据分成了2/2 = 1 组

        for (int i = 1; i < arr.length; i++) {
            // 遍历各组中所有元素
            for (int j = i - 1; j >= 0; j -= 1) {
                // 如归当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println("第3轮");
        System.out.println(Arrays.toString(arr));


    }
}
