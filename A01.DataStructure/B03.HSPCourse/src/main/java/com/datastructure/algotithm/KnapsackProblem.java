package com.datastructure.algotithm;

public class KnapsackProblem {

    public static void main(String[] args) {
        int[] w = {1, 4, 3};
        int[] val = {1500, 3000, 2000};

        int m = 4;
        int n = val.length;


        //创建二维数组
        int[][] v = new int[n + 1][m + 1];
        // 为了记录放入商品的情况，我们顶一个二维数组
        int[][] path = new int[n + 1][m + 1];

        // 初始化第一行和第一列，这里在本程序中，可以不去处理，硬卧默认就是0

        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0; // 将第一列设置为0
        }

        for (int i = 0; i < v.length; i++) {
            v[0][i] = 0; // 将第一行设置为0
        }

        // 根据前面得到共识来动态规划处理
        for (int i = 1; i < v.length; i++) { // 不处理第二列
            for (int j = 1; j < v[0].length; j++) {// 不处理第一列

                if (w[i - 1] > j) {
                    // 因为我们程序i是从1开始的，因此原来公式中的w[i]修改成w[i-1]
                    v[i][j] = v[i - 1][j];
                } else {
                    //v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);

                    //
                    int newVal = val[i - 1] + v[i - 1][j - w[i - 1]];
                    if (v[i - 1][j] < newVal) {
                        v[i][j] = newVal;
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }

                }
            }
        }

        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.println(v[i][j] + " ");
            }
            System.out.println();
        }

        // 输出最后我们是放入的那些商品
        // 遍历path
//        for (int i = 0; i < path.length; i++) {
//            for (int j = 0; j < path[i].length; j++) {
//                if (path[i][j] == 1) {
//                    System.out.printf("第%d个商品放入到背包\n", i);
//                }
//            }
//        }


        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }

}