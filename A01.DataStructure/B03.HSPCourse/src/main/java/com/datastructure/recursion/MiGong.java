package com.datastructure.recursion;

import java.util.Arrays;

public class MiGong {


    public static void main(String[] args) {


        int[][] map = new int[8][7];
        // 使用1表示墙

        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;

        }

        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        setWay(map, 1, 1);

        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

    }


    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {// 当前这个点还没有走过
                // 按照策略 下->右->上->左 走
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {// 向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {// 向右
                    return true;
                } else if (setWay(map, i - 1, j)) {// 向上
                    return true;
                } else if (setWay(map, i, j - 1)) {// 向左走
                    return true;
                } else {
                    // 说明该点事走不通的，是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { // map[i][j] != 0 可能是1，2，3
                return false;
            }
        }
    }

}
