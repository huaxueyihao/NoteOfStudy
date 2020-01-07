package com.leetcode.chapter01_10;

public class Solution09 {

    public static void main(String[] args) {

        System.out.println(isPalindrome(1000000001));

    }


    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String str = String.valueOf(x);
        for (int i = 0, j = str.length() - 1; i <= j; i++, j--) {
            char c = str.charAt(i);
            char c1 = str.charAt(j);
            System.out.println("c = " + c + " , c1 =" + c1 + " i = " + i + " , j= " + j);
            if (c != c1) {
                return false;
            }
        }
        return true;
    }

}
