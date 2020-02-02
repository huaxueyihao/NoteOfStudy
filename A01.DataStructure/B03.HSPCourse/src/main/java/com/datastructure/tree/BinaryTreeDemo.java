package com.datastructure.tree;

public class BinaryTreeDemo {


    public static void main(String[] args) {


        BinaryTree binaryTree = new BinaryTree();

        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");


        // 说明，先手动穿件二叉树
        root.setLeft(node2);
        root.setRight(node3);

        node3.setRight(node4);
        node3.setLeft(node5);

        binaryTree.setRoot(root);

        // 前序遍历
        // 1,2,3,4
//        System.out.println("前序遍历");
//        binaryTree.preOrder();
//        System.out.println();
//
//        // 2,1,3,4
//        System.out.println("中序遍历");
//        binaryTree.infixOrder();
//        System.out.println();
//
//        // 2,4,3,1
//        System.out.println("后序遍历");
//        binaryTree.postOrder();

        HeroNode resNode = binaryTree.preOrderSearch(15);
        if (resNode != null) {
            System.out.printf("找到了，信息为no=%d name=%s\n", resNode.getNo(), resNode.getName());
        } else {
            System.out.printf("没有找到no=%d\n", 15);
        }


    }
}

// 定义BinaryTree
class BinaryTree {


    private HeroNode root;

//    public BinaryTree(HeroNode root) {
//        this.root = root;
//    }


    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void delNode(int no) {
        if (root != null) {
            if (root.getNo() == no) {
                root = null;
            } else {
                root.delNode(no);
            }
        } else {
            System.out.println("空树，不能删除");
        }
    }

    // 前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }


    // 后续 遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }


    public HeroNode preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return null;
        }
    }

    public HeroNode infixOrderSearch(int no) {
        if (this.root != null) {
            return this.root.infixOrderSearch(no);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return null;
        }
    }

    public HeroNode postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        } else {
            System.out.println("二叉树为空，无法遍历");
            return null;
        }
    }


}


// 创建HeroNode 节点

class HeroNode {

    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }


    public void delNode(int no) {

        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }


        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }

        if (this.left != null) {
            this.left.delNode(no);
        }

        if (this.right != null) {
            this.right.delNode(no);
        }


    }


    // 前序遍历的方法
    public void preOrder() {
        System.out.println(this); // 先输出父结点

        // 递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }

        // 递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
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


    // 后序遍历
    public void postOrder() {

        if (this.left != null) {
            this.left.postOrder();
        }


        if (this.right != null) {
            this.right.postOrder();
        }

        System.out.println(this);

    }


    public HeroNode preOrderSearch(int no) {
        // 先比较当前结点是不是
        if (this.no == no) {
            return this;
        }
        HeroNode resNode = null;

        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }

        return resNode;
    }

    public HeroNode infixOrderSearch(int no) {

        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.no == no) {
            return this;
        }

        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }

        return resNode;
    }


    public HeroNode postOrderSearch(int no) {

        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }

        if (resNode != null) {
            return resNode;
        }

        if (this.no == no) {
            return this;
        }
        return resNode;

    }

}


//
