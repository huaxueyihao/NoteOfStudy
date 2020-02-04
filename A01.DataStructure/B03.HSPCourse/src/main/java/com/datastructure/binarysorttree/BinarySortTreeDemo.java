package com.datastructure.binarysorttree;

public class BinarySortTreeDemo {


    public static void main(String[] args) {

        int[] arr = {7, 3, 10, 12, 5, 1, 9};

        BinarySortTree binarySortTree = new BinarySortTree();

        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        // 中序遍历的
        binarySortTree.infixOrder();

    }


}

// 创建二叉排序树
class BinarySortTree {

    private Node root;


    // 查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    // 查找父结点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //1.返回以node为根结点的二叉排序树的最小结点的值
    //2.删除node为根节点的二叉排序树的最小结点

    /**
     * @param node 传入的结点(当做二叉排序树的根节点)
     * @return 返回的以node 为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        // 循环的查找左子结点的，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }

        // 删除最小结点
        delNode(target.value);
        return target.value;
    }

    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            Node targetNode = search(value);
            // 没有找到
            if (targetNode != null) {
                return;
            }
            // 如果发现targetNode没有父结点，说明是根节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }

            // 去targetNode查找父结点
            Node parent = searchParent(value);
            //如果删除的节点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                // 判断targetNode是父结点的左子结点，还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                // 删除两颗子树
                int minValue = delRightTreeMin(targetNode.right);
                targetNode.value = value;

            } else {
                // 删除只有一颗子树的结点
                // 如果要删除的节点有左子结点
                if (targetNode.left != null) {
                    if (parent != null) {
                        // 若果targetNode是parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {// targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }

                } else {
                    if (parent != null) {
                        // 如果要删除的二节点是右子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {
                            //如果targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }
                }

            }

        }
    }


    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }


}


// 创建节点
class Node {


    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }


    /**
     * 查找要删除的节点
     *
     * @param value
     * @return
     */
    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {// 向左子树递归查找
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);

        } else { //向右子树递归查找
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }

    }

    // 查找要删除结点的父结点

    /**
     * @param value 要找的节点的值
     * @return 返回是要删除的结点的父结点，如果没有就返回null
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            // 如果查找的值小于当前结点的值，并且当前结点的左子结点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);
            } else {
                return null;
            }
        }
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    // 添加结点的方法
    // 递归的形式添加结点，主需要需要满足二叉排序树的要求
    public void add(Node node) {
        if (node == null) {
            return;
        }

        // 判断传入的节点的值，和当前子树的根节点的值关系
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                // 递归的向左子树添加
                this.left.add(node);
            }
        } else {//
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }


}
