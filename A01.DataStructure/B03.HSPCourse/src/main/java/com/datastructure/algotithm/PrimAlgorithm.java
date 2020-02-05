package com.datastructure.algotithm;

import java.util.Arrays;

public class PrimAlgorithm {


    public static void main(String[] args) {
        // 测试看看图是否穿件ok
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;

        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},
        };

        // 创建MGraph对象
        MGraph mGraph = new MGraph(verxs);
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph, verxs, data, weight);

        // 输出
        minTree.showGraph(mGraph);

        minTree.prim(mGraph, 1);


    }

}

// 创建最小生成树->村庄的图
class MinTree {
    // 创建图的邻接矩阵


    /**
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data
     * @param weight
     */
    public void createGraph(MGraph graph, int verxs, char[] data, int[][] weight) {

        int i, j;

        for (i = 0; i < verxs; i++) {
            graph.data[i] = data[i];

            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }


    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    // 编写prim算法，得到最小生成树

    /**
     * @param graph
     * @param v     表示从图的第几个定点开始生成
     */
    public void prim(MGraph graph, int v) {
        // 标记定点是否被访问过
        int[] visited = new int[graph.verxs];
        // visited[] 默认元素的值都是0，表示没有访问过

        // 把当期那这个节点标记为已访问
        visited[v] = 1;
        // h1和h2记录两个定点的下边
        int h1 = -1, h2 = -1;
        int minWeight = 10000;

        for (int k = 1; k < graph.verxs; k++) {
            // 因为有graph.verxs顶点，普里姆算法结束户，有graph.verxs-1边

            for (int i = 0; i < graph.verxs; i++) {
                for (int j = 0; j < graph.verxs; j++) {
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        // 替换minWeight 寻找已经访问过的及诶单和未访问过的节点间的权值最小的边
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }

            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值：" + minWeight);
            visited[h2] = 1;
            // minWeight 重新
            minWeight = 10000;
        }


    }


}


class MGraph {

    int verxs;// 表示图的结点个数
    char[] data; // 存放结点数据
    int[][] weight;// 存放边，邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }


}
