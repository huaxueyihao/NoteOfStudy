package com.datastructure.algotithm;

import org.omg.CORBA.INTERNAL;

import javax.swing.text.EditorKit;
import java.util.Arrays;

public class KruskalCase {

    private int edgeNum; // 边的个数
    private char[] vertexs;// 顶点数组
    private int[][] matrix;// 邻接矩阵

    // 使用INF表示连个顶点不能联通
    private static final int INF = Integer.MAX_VALUE;


    public static void main(String[] args) {

        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 12, INF, INF, INF, 16, 14},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {14, INF, INF, INF, 8, 9, 0},
        };

        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();

//        EData[] edges = kruskalCase.getEdges();
//
//        kruskalCase.sortEdges(edges);
//        System.out.println(Arrays.toString(edges));

        kruskalCase.kruskal();


    }

    public KruskalCase(char[] vertexs, int[][] matrix) {
        // 初始化顶点数和边的个数
        int vlen = vertexs.length;
        // 初始化顶点
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }

        // 初始化边
        this.matrix = new int[vlen][vlen];

        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        // 统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    this.edgeNum++;
                }
            }
        }

    }


    public void kruskal() {
        int index = 0;// 表示最后结果数组的索引
        int[] ends = new int[edgeNum]; // 用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点

        // 创建结果数组,保存最后的最小生成树
        EData[] rets = new EData[edgeNum];

        // 获取图中，所有的边的集合，一共有12条边
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges) + " 共" + edges.length);

        // 按照边的权值大小进行排序(从小到大)
        sortEdges(edges);

        // 遍历edges, 将边添加到最小生成树中，判断是准备加入的边是否形成了回路，如果没有，就加入rets

        for (int i = 0; i < edgeNum; i++) {
            // 获取到第i条边的地一个顶点(起点)
            int p1 = getPosition(edges[i].start);
            int p2 = getPosition(edges[i].end);

            // 获取p1这个顶点在已有的最小生成树中的终点
            int m = getEnd(ends, p1);
            // 获取p2这个顶点在已有的最小生成树中的终点
            int n = getEnd(ends, p2);

            // 是否构成回路
            if (m != n) {// 不构成回路
                ends[m] = n; //设置m在"已有最小生成树"中的终点
                rets[index++] = edges[i];// 有一条边计入到结果中
            }
        }

        // 统计并打印
        System.out.println("最小生成树=" + Arrays.toString(rets));

    }

    // 打印邻接矩阵
    public void print() {
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边进行排序处理，冒泡

    private void sortEdges(EData[] edges) {
        EData temp = null;
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    temp = edges[j + 1];
                    edges[j + 1] = edges[j];
                    edges[j] = temp;
                }
            }
        }
    }

    //

    /**
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch对应的下标
     */
    public int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中的边，放到EData[]中，后面需要遍历该数组
     * 通过matrix 邻接矩阵来获取
     * EData[] 形式['A','B',12],['B','F',7]
     *
     * @return
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];

        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 获取顶点为i的终点，用于后面判断两个顶点的终点是否相同
     *
     * @param ends 记录了各个顶点对应的终点是哪个，ends数组是在遍历过程中，逐步形成
     * @param i    传入的顶点对应的下标
     * @return 返回下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }


}

// 创建一个类EData，它的对象实例就表示一条边

class EData {
    char start;// 边的一个点
    char end; // 边的另外一个点
    int weight; // 边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
