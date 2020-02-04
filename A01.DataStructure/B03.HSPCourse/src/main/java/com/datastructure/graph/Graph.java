package com.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {

    private ArrayList<String> vertexist;// 存储顶点集合
    private int[][] edges;
    private int numOfEdges;

    private boolean[] isVisited;

    public static void main(String[] args) {

        int n = 5;
        String[] vertexValue = {"A", "B", "C", "D", "E"};
        // 创建图对象
        Graph graph = new Graph(n);
        for (String value : vertexValue) {
            graph.insertVertex(value);
        }
        // 添加边
        // A-C A-B B-C B-D B-E
        graph.insertEdge(0, 1, 1); // A-B
        graph.insertEdge(0, 2, 1); // A-C
        graph.insertEdge(1, 2, 1); // B-C
        graph.insertEdge(1, 3, 1); // B-D
        graph.insertEdge(1, 4, 1); // B-E


        graph.showGraph();

//        graph.dfs();

        graph.bfs();

    }


    public Graph(int n) {
        // 初始化矩阵
        edges = new int[n][n];
        vertexist = new ArrayList<>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }


    // 深度优先算法
    // i 第一次就是0
    public void dfs(boolean[] isVisited, int i) {
        // 首先我们访问该结点，输出
        System.out.print(getValueByIndex(i) + "->");
        isVisited[i] = true;

        // 查找结点i的第一个邻接结点w
        int w = getFirstNeighbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            // 如果w结点已经被访问过
            w = getNextNeighbor(i, w);
        }

    }


    // 对dfs，进行一个重载，遍历我们所有的结点，并进行dfs
    public void dfs() {
        // 遍历所有的结点，进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }


    // 对一个结点进行广度优先遍历的方法
    private void bfs(boolean[] isVisited, int i) {
        int u; // 表示对列的头结点对应下标
        int w; // 邻接结点w
        // 对列，记录节点访问的顺序

        LinkedList queue = new LinkedList();
        // 访问结点，输出结点信息
        System.out.print(getValueByIndex(i) + "=>");
        // 标记为已访问
        isVisited[i] = true;
        // 将结点加入对列
        queue.addLast(i);

        while (!queue.isEmpty()) {
            //取出对列的头结点下标
            u = (Integer) queue.removeFirst();
            // 得到第一个邻结点的下标w
            w = getFirstNeighbor(u);

            while (w != -1) {// 找到
                // 是否访问过
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "=>");
                    // 标记已经访问
                    isVisited[w] = true;
                    // 入队
                    queue.addLast(w);
                    //
                }
                // 以u为前驱点，找w后面的下一个邻结点
                w = getNextNeighbor(u, w); // 体现出广度优先
            }
        }
    }

    public void bfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }


    // 根据前一个邻结点的下标来获取下一个邻接结点
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexist.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }


    /**
     * 得到第一个邻接结点的下标w
     *
     * @param index
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexist.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }


    // 图中常用的方法
    // 返回结点的个数
    public int getNumOfVertex() {
        return vertexist.size();
    }

    // 显示图对应的举证
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    // 得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //
    public String getValueByIndex(int i) {
        return vertexist.get(i);
    }

    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }


    // 插入结点
    public void insertVertex(String vertex) {
        vertexist.add(vertex);
    }

    // 添加边
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }


}
