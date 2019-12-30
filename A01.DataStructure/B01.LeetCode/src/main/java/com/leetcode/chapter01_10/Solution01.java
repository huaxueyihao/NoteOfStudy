package com.leetcode.chapter01_10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 */
public class Solution01 {

    public static void main(String[] args) {

        int[] nums = new int[]{3, 2, 4};
        int target = 6;

//        int[] result = twoSum(nums, target);
        int[] result = oneLoop(nums, target);
        System.out.println(Arrays.toString(result));

    }

    private static int[] oneLoop(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int result = target - nums[i];
            if (map.containsKey(result)) {
                return new int[]{i, map.get(result)};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoeparateLoop(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int result = target - num;

            if (map.containsKey(result) && map.get(result) != i) {
                return new int[]{i, map.get(result)};
            }
        }
        return null;
    }

    /**
     * 时间复杂度为O(n^2)
     * 空间复杂度为O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoNestedLoop(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {

            for (int j = i + 1; j < nums.length; j++) {
                int sum = nums[i] + nums[j];
                if (sum == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

}
