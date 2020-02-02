package com.datastructure.search;

import java.util.Arrays;

public class FibonacciSearch {


    public static int maxSize = 20;

    public static void main(String[] args) {

        int[] arr = {1, 8, 10, 89, 1000, 1234};

        fibSearch(arr,10);


    }


    // 非递归方法得到一个斐波那契数列
    public static int[] fib() {
        int[] f = new int[maxSize];

        f[0] = 1;
        f[1] = 1;

        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }


    // 编写菲薄拿起查找算法
    public static int fibSearch(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;
        int k = 0;// 表示斐波那契分割数值的下标

        int mid = 0;
        int f[] = fib();

        // 获取到斐波那契分割数值的下标
        while (high > f[k] - 1) {
            k++;
        }
        // 应为f[k]值可能大于a的长度，需要使用Arrays类，构造一个新的数组，构造一个新的数组
        int[] temp = Arrays.copyOf(a, f[k]);

        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }

        // 使用while来循环处理，找到我们的数key
        while (low < high) {
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {// 我们应该继续向数组的前面查找
                high = mid - 1;
                // 1.全部元素 = 前面的元素 + 后边的元素
                // 2. f[k] = f[k-1] +f[k-2]
                // 因为 前面有f[k-1]个元素，所以可以继续拆分 f[k-1] = f[k-2] + f[k-3]
                // 即 在f[k-1]的前面继续查找k--
                // 即下次循环mid = f[k-1-1]-1
                k--;
            } else if (key > temp[mid]) {

                low = mid + 1;
                // 1. 全部元素 = 前面的元素 + 后边的元素
                // 2. f[k] = f[k-1]+ f[k-2]
                // 3. 应为后面我们又f[k-2] 所以可以继续拆分f[k-1] = f[k-3] = f[k-4]
                // 4. 即在f[k-2] 的前面查找k -=2
                // 5. 即下次循环mid = f[k-1 -2 ] -1
                k -= 2;
            } else { // 找到
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }

        return -1;

    }


}
