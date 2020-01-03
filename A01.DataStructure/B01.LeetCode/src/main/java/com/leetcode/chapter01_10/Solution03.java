package com.leetcode.chapter01_10;

import java.util.*;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class Solution03 {

    public static void main(String[] args) {


        String str =
                "hohmm";

        // 将字符串 按照长度 分成不同的字符创，先分成最长的，若是没有重复的就是最长的


//        int a = testMethod01(str);
//        System.out.println(a);
//
//
//        int b = testMethod02(str);


        int c = testMethod03(str);
        System.out.println(c);

    }


    private static int testMethod03(String str) {
        int n = str.length(), ans = 0, flag = 0;

        int start = 0;
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);

            // 以i往前找，前前面找到，这长度
            // 以i往后找，后面找到则，长度为
//            int bIndex = str.lastIndexOf(c, i - 1);
//            int blen = 0;
//            if (bIndex > 0) {
//                // 前面的最长子串
//                int temp = i - bIndex;
//                blen = temp;
//            } else {
//                blen = i;
//            }
            int aIndex = str.indexOf(c, i + 1);
            int alen = 0;
            if (aIndex > 0) {
                // 后面的长子串
                int temp = aIndex - start;
                start = Math.max(i, start);
                alen = temp;
            }
            ans = Math.max(alen, ans);
            System.out.println("i = " + i + ", c = " + c + ", alen=" + alen + ", ans = " + ans + ",start = " + start);
        }
        return ans;
    }

    private static int testMethod02(String str) {
        int n = str.length(), ans = 0;
        // 创建map窗口，i为左区间，j为右区间，右边界移动
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        // i是已经检查过的字符串的没有没有重复字符的开始处
        // 比如："abcbad"
        // j = 0 ，则i = 0 ，map = [{key = 'a',value = 1}]
        // j = 1 , 则i = 0，map = [{key = 'a',value = 1},{key = 'b',value = 2}]
        // j = 2 , 则i = 0，map = [{key = 'a',value = 1},{key = 'b',value = 2},{key = 'c',value = 3}]
        // j = 3 , 则i = 2，map = [{key = 'a',value = 1},{key = 'b',value = 2},{key = 'c',value = 3},{key = 'b',value = 4}]
        // 这里第一次出现了重复字符b，也就是i=0至j=3之间有重复字符是b，那么要想区间子串[i,j]不重复，则需要将i的值移动到重复字符的下个位置
        // j = 4 , 则i = 2，map = [{key = 'a',value = 1},{key = 'b',value = 2},{key = 'c',value = 3},{key = 'b',value = 4},{key = 'a',value = 5}]
        // 这里进行了i = 2 与map.get('a') = 1的大小比较，若是不做取大值的比较，直接将i = map.get('a') = 1 的话，则这时候区间为子串为bcba,其中b就重复了，所以可以看出来，i的作用是标识区间子串不存在重复字符的开始处
        // j = 5 , 则i = 2，map = [{key = 'a',value = 1},{key = 'b',value = 2},{key = 'c',value = 3},{key = 'b',value = 4},{key = 'a',value = 5}，{key = 'd',value = 6}]

        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(str.charAt(j))) {
                // 记录重复的开始位置
                // 左边界移动到 相同字符的下一个位置和i当前位置中更靠右的位置，这样是为了防止i向左移动
                i = Math.max(map.get(str.charAt(j)), i);
            }
            // 比对当前无重复字段长度和储存的长度，选最大值替换
            // j-i+1是因为此时i,j索引人处于不重复的位置，j还没有向后移动，取的[i,j]长度
            ans = Math.max(ans, j - i + 1);
            map.put(str.charAt(j), j + 1);
        }
        return ans;
    }

    private static int testMethod01(String str) {

        int n = str.length();
        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (allUnique(str, i, j)) {
                    ans = Math.max(ans, j - i);
                }
            }
        }
        return ans;
    }

    private static boolean allUnique(String str, int start, int end) {

        Set<Character> set = new HashSet<Character>();
        for (int i = start; i < end; i++) {
            char c = str.charAt(i);
            if (set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }


}
