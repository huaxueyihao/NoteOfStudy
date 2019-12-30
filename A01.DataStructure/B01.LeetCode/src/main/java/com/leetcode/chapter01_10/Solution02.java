package com.leetcode.chapter01_10;

import java.util.List;
import java.util.Stack;

public class Solution02 {

    public static void main(String[] args) {

        ListNode listNode2 = new ListNode(5);
//        ListNode listNode4 = new ListNode(4);
//        ListNode listNode3 = new ListNode(3);

//        listNode2.next = listNode4;
//        listNode4.next = listNode3;


        ListNode listNode5 = new ListNode(5);
//        ListNode listNode6 = new ListNode(6);
//        ListNode listNode4_ = new ListNode(4);

//        listNode5.next = listNode6;
//        listNode6.next = listNode4_;


        ListNode result = addTwoNumbers2(listNode2, listNode5);
        System.out.println(result);

    }

    private static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode tl1 = l1;
        ListNode tl2 = l2;

        int nextNeedAddVal = 0;
        int val = 0;
        ListNode head = null,// 头结点
                preNode = null,// 前一个结点，用于把给前一个结点赋next的值
                node = null;

        while (tl1 != null || tl2 != null || nextNeedAddVal != 0) {

            if (tl1 != null && tl2 != null) {
                val = (tl1.val + tl2.val + nextNeedAddVal) % 10;
                nextNeedAddVal = (tl1.val + tl2.val + nextNeedAddVal) / 10;
            }
            if (tl1 != null && tl2 == null) {
                val = (tl1.val + 0 + nextNeedAddVal) % 10;
                nextNeedAddVal = (tl1.val + 0 + nextNeedAddVal) / 10;
            }

            if (tl1 == null && tl2 != null) {
                val = (0 + tl2.val + nextNeedAddVal) % 10;
                nextNeedAddVal = (0 + tl2.val + nextNeedAddVal) / 10;
            }

            if ((tl1 == null && tl2 == null) && nextNeedAddVal != 0) {
                preNode.next = new ListNode(nextNeedAddVal);
                break;
            }

            if (tl1 == null && tl2 == null && nextNeedAddVal == 0) {
                break;
            }


            node = new ListNode(val);
            if (head == null) {
                head = node;
            }
            if (preNode != null) {
                preNode.next = node;
            }
            preNode = node;

            tl1 = tl1== null ? null : tl1.next ;
            tl2 = tl2== null ? null : tl2.next ;
        }
        return head;
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int l1num = listNode2Num(l1);
        int l2num = listNode2Num(l2);

        int result = l1num + l2num;


        ListNode listNode = num2ListNode(result);
//        String str = Integer.toString(result);
//        int length = str.length();
//        // 递归
//        ListNode listNode = rescurive(length, result);


        return listNode;
    }

    private static ListNode rescuriveSortAsc(int length, int result) {
        length = length - 1;
        double pow = Math.pow(10, length);
        int temp = (int) (result % pow);
        int nodeVal = (int) (result / pow);
        ListNode listNode = new ListNode(nodeVal);
        if (length < 0) {
            listNode.next = rescuriveSortAsc(length, temp);
        }
        return listNode;
    }

    private static ListNode num2ListNode(int result) {

        String str = Integer.toString(result);
        int length = str.length();

        ListNode listNode = rescuriveSortDesc(length, result);


        return listNode;
    }

    private static ListNode rescuriveSortDesc(int length, int result) {
        int val = (int) (result % 10);
        result = (int) (result / 10);
        length--;
        ListNode listNode = new ListNode(val);
        if (length > 0) {
            listNode.next = rescuriveSortDesc(length, result);
        }
        return listNode;
    }


    // 将链表进行数字化
    private static int listNode2Num(ListNode l) {
        int int10 = 1;
        int target = 0;

        ListNode temp = l;
        while (temp != null) {
            int val = temp.val;
            target += val * int10;
            int10 = int10 * 10;
            temp = temp.next;
        }
        return target;
    }


}
