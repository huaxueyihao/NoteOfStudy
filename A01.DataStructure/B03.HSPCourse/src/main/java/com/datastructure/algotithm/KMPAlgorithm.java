package com.datastructure.algotithm;

import java.util.Arrays;

public class KMPAlgorithm {

    public static void main(String[] args) {

        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

        int[] next = kmpNext(str2);
        System.out.println(Arrays.toString(next));

        int index = kmpSearch(str1, str2, next);
        System.out.println(index);


    }

    public static int kmpSearch(String str1, String str2, int[] next) {

        // 遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {

            // 需要处理str1.charAt(i) != str2.charAt(j)
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }


            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }

            if (j == str2.length()) { // 找到
                return i - j + 1;
            }


        }

        return -1;

    }

    // 获取到一个字符串(子串)的部分匹配值
    public static int[] kmpNext(String dest) {
        // 创建一个next数组 保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0;//如果字符串时长度为1 部分匹配值就是0

        for (int i = 1, j = 0; i < dest.length(); i++) {
            // 当dest.charAt(i) != dest.chatAt(j),需要从next[j-1]获取新的j
            // 知道发现dest.charAt(i) == dest.chatAt(j)成立才推出
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }

            // 当dest.charAt(i) == dest.chatAt(j) 满足时，部分匹配值就是+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;

    }


}
