package com.datastructure.avl;

public class AVLTreeDemo {

    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 7, 8}; // 左旋转
//        int[] arr = {10, 12, 8, 9, 7, 6}; // 右旋转
        int[] arr = {10, 11, 7, 6, 8, 9};
        // 创建一个AVLTree
        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }

        // 遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();
        System.out.println("height=" + avlTree.height() + " leftHeight = " + avlTree.leftHeight() + " rightHeight = " + avlTree.rightHeight());

    }


}


// 创建avl树
class AVLTree {

    private Node root;


    public int height() {
        return root == null ? 0 : root.height();
    }

    public int leftHeight() {
        if (root == null) {
            return 0;
        }

        return root.left == null ? 0 : root.left.height();
    }

    public int rightHeight() {
        if (root == null) {
            return 0;
        }

        return root.right == null ? 0 : root.right.height();
    }


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

    // 返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    // 返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }


    //返回当前结点的高度，以该结点为根结点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }


    // 左旋转
    private void leftRotate() {
        // 创建新的结点，以当前根节点的值
        Node newNode = new Node(value);
        // 把新的节点的左子树设置成当前结点的左子树
        newNode.left = left;
        // 把新结点的右子树设置成当前结点的右子树的左子树
        newNode.right = right == null ? null : right.left;
        // 把当前结点的值替换成右子结点的值
        value = right == null ? 0 : right.value;
        // 把当前结点的右子树设置成当前结点右子树的右子树
        if (right != null) {
            right = right.right;
        }
        // 把当前结点的左子树设置成新的结点
        left = newNode;
    }

    // 右旋转
    private void rightRotate() {
        // 创建新的结点，以当前根节点的值
        Node newNode = new Node(value);
        // 把新的节点的左子树设置成当前结点的右子树
        newNode.right = right;
        // 把新结点的左子树设置成当前结点的左子树的右子树
        newNode.left = left == null ? null : left.right;
        // 把当前结点的值替换成左子结点的值
        value = left == null ? 0 : left.value;
        // 把当前结点的左子树设置成当前结点左子树的左子树
        if (left != null) {
            left = left.left;
        }
        // 把当前结点的右子树设置成新的结点
        right = newNode;
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

        //当添加完一个结点后，如果 右子树的高度-左子树的高度> 1,左旋转
        if (rightHeight() - leftHeight() > 1) {
            if (right != null && right.rightHeight() < right.leftHeight()) {
                // 先对右子树进行
                right.rightRotate();
                leftRotate();
            } else {
                leftRotate();
            }
            return;
        }

        // 当添加完一个结点后，如果 左子树的高度-右子树的高度> 1,右旋转
        if (leftHeight() - rightHeight() > 1) {
            if (left != null && left.rightHeight() > left.leftHeight()) {
                left.leftRotate();
                rightRotate();
            } else {
                rightRotate();
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

