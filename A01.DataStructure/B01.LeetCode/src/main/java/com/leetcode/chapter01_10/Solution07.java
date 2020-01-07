package com.leetcode.chapter01_10;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转
 * 输入: 123
 * 输出: 321
 * <p>
 * 输入: -123
 * 输出: -321
 * <p>
 * 输入: 120
 * 输出: 21
 * <p>
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0
 */
public class Solution07 {

    public static void main(String[] args) {


        int reverse = reverse(1563847412);
        System.out.println(reverse);

//        System.out.println(Long.MAX_VALUE);

//        double pow = Math.pow(2, 32);
//        System.out.println(pow);

//        System.out.println(-102%10);


    }


    public static int reverse(int x) {
        long absx =  x;
        int length = String.valueOf(Math.abs(x)).length();
        long target = 0;
        int step = 10;
        while (length > 0) {
            long mod = absx % step;
            absx = absx / step;
            length--;
            double reverse = mod * Math.pow(10, length);
            target += reverse;
        }
        if (target < -(1L << 31) || target > ((1L << 31) - 1)) {
            return 0;
        }
        return (int) target;
    }

}
