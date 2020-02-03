package com.datastructure.tree;

import java.util.Arrays;

public class HeapSort {


    public static void main(String[] args) {

        int[] arr = {4, 6, 8, 5, 9};

        //
//        adjustHeap(arr, 1, arr.length);
//
//        System.out.println("第一次调整" + Arrays.toString(arr));
//
//        adjustHeap(arr, 0, arr.length);
//
//        System.out.println("第二次调整" + Arrays.toString(arr));


        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        System.out.println("第一次调整" + Arrays.toString(arr));


        /**
         * 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
         * 重新调整结构，时期满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，知道整个序列有序
         */
        int temp = 0;
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);

            System.out.println("i=" + j + "  " + Arrays.toString(arr));
        }

        System.out.println("数组：" + Arrays.toString(arr));

    }


    public static void heapSort(int arr[]) {
        System.out.println("对排序");
    }


    /**
     * 功能：完成将以i对应的非叶子结点的树调整成大顶堆
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子结点在数组中索引
     * @param length 表示对多少个元素继续调整，length是在逐渐的减少
     */
    public static void adjustHeap(int arr[], int i, int length) {

        int temp = arr[i];

        // 1. k = i * 2 + 1是i结点的左子结点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 左子结点小于右子结点
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++; // k指向由子结点
            }

            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k; // 让i指向k，继续循环比较
            } else {
                break;
            }

//            arr[k] = temp;
        }

        // 当for 循环结束后，我们已经将以i为父结点的树的最大值，房子啊了最顶(局部)

        arr[i] = temp;
    }


}
