package com.datastructure.tree.hufmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {


    public static void main(String[] args) {

        int[] arr = {13, 7, 8, 3, 29, 6, 1};

        Node root = createHuffmanTree(arr);

        preOrder(root);


    }


    public static Node createHuffmanTree(int[] arr) {
        // 第一步为了操作方便
        // 1. 遍历arr数组
        // 2. 将arr的每个元素构成一个Node
        // 3. 将Node方法ArrayList

        List<Node> nodes = new ArrayList<>();

        for (int value : arr) {
            nodes.add(new Node(value));
        }


        while (nodes.size() > 1) {
            // 排序 从小到大
            Collections.sort(nodes);
            System.out.println(nodes);

            // 取出根节点权值最小的两个二叉树
            // 1. 取出权值最小的结点
            Node leftNode = nodes.get(0);
            // 2. 取出权值第二小的结点(二叉树)
            Node rightNode = nodes.get(1);

            //3.构建一颗心的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);

            parent.left = leftNode;
            parent.right = rightNode;
            // 4 从ArrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }

        // 返回霍夫曼树根节点

        return nodes.get(0);

    }

    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空霍夫曼树");
        }
    }


}

// 创建结点类
// 为了让Node 对象支持排序Collections集合排序
// 让Node 实现Comparable接口
class Node implements Comparable<Node> {

    int value;

    Node left;
    Node right;


    public void preOrder() {
        System.out.println(this);

        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }


    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
