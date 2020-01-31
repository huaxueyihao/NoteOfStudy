package com.datastructure.linkedlist;

public class DoubleLinkedListDemo {


    public static void main(String[] args) {

        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        DoubleLinkedList list = new DoubleLinkedList();

        list.add(hero1);
        list.add(hero2);
        list.add(hero3);
        list.add(hero4);

        list.list();
    }

}

class DoubleLinkedList {

    private HeroNode2 head = new HeroNode2(0, "", "");


    // 显示链表
    public void list() {
        // 空判断
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }


    public void add(HeroNode2 headNode) {
        HeroNode2 temp = head;

        while (true) {
            if (temp.next == null) {
                break;
            }

            temp = temp.next;
        }
        temp.next = headNode;
        headNode.pre = temp;
    }


    public void update(HeroNode2 node) {
        if (head.next == null) {
            System.out.println("空链表");
            return;
        }

        // 找到需要修改的节点，根据no编号
        HeroNode2 temp = head.next;
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

        if (head.next == null) {
            System.out.println("空");
            return;
        }

        HeroNode2 temp = head;
        boolean flag = false;

        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.no == no) {
                // 找到待删除结点的前一个结点
                flag = true;
                break;
            }
            temp = temp.next;

        }
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null)
                temp.next.pre = temp.pre;
            temp.pre = null;
            temp.next = null;


        } else {
            System.out.println("没有找到");
        }

    }


}

// 穿件

class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickname) {
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
