package com.book.study.zen.chapter21.democ;

import java.util.ArrayList;

public class Client {


    public static void main(String[] args) {


    }

    // 遍历整颗树，只要给我根节点，我就能遍历出所有的节点
    public static String getTreeInfo(Branch root) {
        ArrayList<Corp> subordinateList = root.getSubordinate();
        String info = "";
        for (Corp s : subordinateList) {
            if (s instanceof Leaf) {
                info = info + s.getInfo() + "\n";
            } else {
                info = info + s.getInfo() + "\n" + getTreeInfo((Branch) s);
            }
        }
        return info;
    }


}
