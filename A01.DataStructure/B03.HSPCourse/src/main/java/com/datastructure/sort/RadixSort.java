package com.datastructure.sort;

import java.util.Arrays;

public class RadixSort {

    public static void main(String[] args) {
        int arr[] = {53, 3, 542, 748, 14, 214};


        Arrays.sort(arr);


        radixSort(arr);


    }

    private static void radixSort(int[] arr) {

        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int maxLength = (max + "").length();


        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际存放了多少个数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        int[] bucketElementCounts = new int[10];

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 第一轮()
            for (int j = 0; j < arr.length; j++) {
                int digitOfElement = arr[j] / n % 10;
                // 放到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;

            }
            // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
            int index = 0;
            // 遍历每一桶，并将桶中是数据，放入到原数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                // 如果桶中，有数据，我们才放入到原数组
                if (bucketElementCounts[k] != 0) {
                    // 循环该桶即第k个桶(即第k个一维数组)，放入
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        // 取出元素放入到arr
                        arr[index++] = bucket[k][l];

                    }
                }
                // 第i+1轮处理后，需要将每个bucketElementCounts[k] = 0 ！！！！
                bucketElementCounts[k] = 0;
            }


            System.out.println("第" + (i + 1) + "轮，对个位的排序处理arr=" + Arrays.toString(arr));


        }


    }

    // 基数排序方法
    public static void radixSortStep(int[] arr) {

        // 第1轮（针对每个元素的个位数进行排序处理）

        // 说明
        // 1. 二维数组包含10个一维数组
        // 2. 为了防止在放入数的时候，数据溢出，则每个一维数组，大小
        // 3. 为明确，基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际存放了多少个数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        int[] bucketElementCounts = new int[10];

        // 第一轮()
        for (int j = 0; j < arr.length; j++) {
            int digitOfElement = arr[j] % 10;
            // 放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;

        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        int index = 0;
        // 遍历每一桶，并将桶中是数据，放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中，有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组)，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr
                    arr[index++] = bucket[k][l];

                }
            }
            // 第一轮处理后，需要将每个bucketElementCounts[k] = 0 ！！！！
            bucketElementCounts[k] = 0;
        }


        System.out.println("第一轮，对个位的排序处理arr=" + Arrays.toString(arr));


        // 第2轮()
        for (int j = 0; j < arr.length; j++) {
            int digitOfElement = arr[j] / 10 % 10;
            // 放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;

        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        index = 0;
        // 遍历每一桶，并将桶中是数据，放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中，有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组)，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr
                    arr[index++] = bucket[k][l];

                }
            }
            // 第一轮处理后，需要将每个bucketElementCounts[k] = 0 ！！！！
            bucketElementCounts[k] = 0;

        }

        System.out.println("第2轮，对个位的排序处理arr=" + Arrays.toString(arr));


        // 第3轮()
        for (int j = 0; j < arr.length; j++) {
            int digitOfElement = arr[j] / 100 % 10;
            // 放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;

        }

        // 按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
        index = 0;
        // 遍历每一桶，并将桶中是数据，放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            // 如果桶中，有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶(即第k个一维数组)，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr
                    arr[index++] = bucket[k][l];

                }
            }
            // 第一轮处理后，需要将每个bucketElementCounts[k] = 0 ！！！！
            bucketElementCounts[k] = 0;

        }

        System.out.println("第3轮，对个位的排序处理arr=" + Arrays.toString(arr));

    }


}
