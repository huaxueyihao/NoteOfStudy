package com.book.study.zen.chapter29.demoa;

public class Client {

    public static void main(String[] args) {

        System.out.println("--------房地产公司是这样运行的----------");
        // 先找到我的公司
        HouseCorp houseCorp = new HouseCorp();
        // 看我怎么挣钱
        houseCorp.makeMoney();
        System.out.println("\n");
        System.out.println("---------服装公司是这样运行的---------");
        ClothesCorp clothesCorp = new ClothesCorp();
        clothesCorp.makeMoney();


    }

}
