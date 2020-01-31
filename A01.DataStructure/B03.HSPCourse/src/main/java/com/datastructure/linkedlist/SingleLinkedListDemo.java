package com.datastructure.linkedlist;

import java.util.Stack;

public class SingleLinkedListDemo {

    public static void main(String[] args) {


        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        SingleLinkedList list = new SingleLinkedList();
        list.addByOrder(hero3);
        list.addByOrder(hero1);
        list.addByOrder(hero2);
        list.addByOrder(hero4);


//        System.out.println("反转之前");
//        list.list();
//
//        SingleLinkedList.reversetList(list.getHead());
//
//        System.out.println("反转之后");
//        list.list();

        SingleLinkedList.reversePrint(list.getHead());


    }

}

// 定义SingleLinkedList
class SingleLinkedList {

    // 初始化头结点
    private HeroNode head = new HeroNode(0, "", "");

    // 添加及诶单到单向链表
    // 1.当不考虑编号顺序时，
    // 2.将最后这个节点的next指向新的节点
    public void add(HeroNode heroNode) {
        //
        HeroNode temp = head;
        // 遍历链表，找到最后
        while (true) {
            if (temp.next == null) {
                break;
            }
            // 如果没有找到，将temp后移
            temp = temp.next;
        }

        temp.next = heroNode;
    }

    // 根据排名将英雄插入到指定位置
    // (如果哟这个排名，则添加失败，并给出提示)
    public void addByOrder(HeroNode node) {

        //因为是单链表，我们找的temp是位于添加位置的前一个结点，否则插入不了
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > node.no) {
                break;
            } else if (temp.next.no == node.no) {
                // 编号存在
                flag = true;
                break;
            }

            temp = temp.next;

        }

        if (flag) {
            //不能添加
            System.out.printf("准备插入的英雄的编号%d已经存在了，不能加入\n", node.no);
        } else {
            node.next = temp.next;
            temp.next = node;
        }

    }

    public void update(HeroNode node) {
        if (head.next == null) {
            System.out.println("空链表");
            return;
        }

        // 找到需要修改的节点，根据no编号
        HeroNode temp = head.next;
        boolean flag = false;

        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;

        }
        if (flag) {
            temp.name = node.name;
            temp.nickname = node.nickname;
        } else {
            System.out.printf("没有找到编号%d的节点，不能修改\n", node.no);
        }
    }


    public void del(int no) {

        HeroNode temp = head;
        boolean flag = false;

        while (true) {
            if (temp.next == null) {
                break;
            }

            if (temp.next.no == no) {
                // 找到待删除结点的前一个结点
                flag = true;
                break;
            }
            temp = temp.next;

        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.println("没有找到");
        }

    }


    // 显示链表
    public void list() {
        // 空判断
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }


    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        // 判断
        if (head.next == null) {
            return null;
        }

        int size = getLength(head);

        if (index <= 0 || index > size) {
            return null;
        }

        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    // 将单链表反转
    public static void reversetList(HeroNode head) {

        if (head.next == null || head.next.next == null) {
            return;
        }

        HeroNode cur = head.next;
        HeroNode next = null; // 指向当前结点

        HeroNode reverseHead = new HeroNode(0, "", "");


        while (cur != null) {
            next = cur.next;
            cur.next = reverseHead.next; // 将cur的下一个节点指向新链表的最前端
            reverseHead.next = cur;
            cur = next;// 让cur后移
        }

        // 将head.next指向reverseHead.next
        head.next = reverseHead.next;
        reverseHead.next = null;

    }

    public static void reversePrint(HeroNode head){
        if(head.next == null){
            return;
        }

        Stack<HeroNode> stack = new Stack<HeroNode>();

        HeroNode cur = head.next;

        // 将链表的所有节点压入
        while (cur != null){
            stack.push(cur);
            cur = cur.next;
        }

        while (stack.size() > 0){
            System.out.println(stack.pop());
        }


    }


    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next; // 遍历
        }
        return length;
    }


    public HeroNode getHead() {
        return head;
    }
}

// 定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}




