package com.datastructure.linkedlist;

public class Joespfu {


    public static void main(String[] args) {

        CircleSingleLinkedList list = new CircleSingleLinkedList();
        list.addBoy(5);


        list.showBoy();

        list.countBy(1, 2, 5);
    }

}

// 环形单向链表
class CircleSingleLinkedList {

    // 创建一个first节点，当前没有编号

    private Boy first = null;

    // 添加小孩节点
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("nums 的值不正确");
        }

        Boy curBoy = null; // 辅助指针，帮助构建环形链表
        // 使用for来创建我们的环形链表

        for (int i = 1; i <= nums; i++) {
            // 根据编号，创建小孩节点
            Boy boy = new Boy(i);
            // 如果是第一个小孩
            if (i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }


    public void showBoy() {
        if (first == null) {
            System.out.println("没有任何小孩~~");
            return;
        }

        // 因为first不能动，因此我们任然使用辅助指针
        Boy curBoy = first;
        while (true) {
            System.out.printf("小孩的编号%d \n", curBoy.getNo());
            if (curBoy.getNext() == first) {
                break;
            }
            curBoy = curBoy.getNext(); // 后移
        }
    }


    // 根据用户的输入，计算出小孩除权的顺序
    public void countBy(int startNo, int countNum, int nums) {
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误，请重新输入");
            return;
        }

        //
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        while (true) {
            if (helper == first) {
                break;
            }

            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // first 指向的节点就是要出圈的节点
            System.out.printf("小孩%d出圈\n", first.getNo());
            //
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.printf("留在权重的小孩编号%d \n", first.getNo());

    }


}


class Boy {

    private int no;

    private Boy next; // 指向下一个结点，默认null

    public Boy(int no) {
        this.no = no;
    }


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
