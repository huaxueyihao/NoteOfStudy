package com.datastructure.sort;

import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        selectSort(arr);


        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));


    }

    public static void selectSort(int[] arr) {


        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            arr[minIndex] = arr[i];
            arr[i] = min;
        }
    }

    public static void selectSortStep(int[] arr) {

        int minIndex = 0;
        int min = arr[0];

        for (int j = 0 + 1; j < arr.length; j++) {
            if (min > arr[j]) {
                min = arr[j];
                minIndex = j;
            }
        }

        arr[minIndex] = arr[0];
        arr[0] = min;
        System.out.println("第1轮后");
        System.out.println(Arrays.toString(arr));


        minIndex = 1;
        min = arr[1];

        for (int j = 1 + 1; j < arr.length; j++) {
            if (min > arr[j]) {
                min = arr[j];
                minIndex = j;
            }
        }

        arr[minIndex] = arr[1];
        arr[1] = min;
        System.out.println("第2轮后");
        System.out.println(Arrays.toString(arr));


        minIndex = 2;
        min = arr[2];

        for (int j = 2 + 1; j < arr.length; j++) {
            if (min > arr[j]) {
                min = arr[j];
                minIndex = j;
            }
        }

        arr[minIndex] = arr[2];
        arr[2] = min;
        System.out.println("第3轮后");
        System.out.println(Arrays.toString(arr));


    }
}
