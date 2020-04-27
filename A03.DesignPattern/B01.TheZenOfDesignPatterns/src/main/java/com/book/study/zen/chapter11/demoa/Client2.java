package com.book.study.zen.chapter11.demoa;

import java.util.ArrayList;

public class Client2 {

    public static void main(String[] args) {


        // 存放run的顺序
        ArrayList<String> sequence = new ArrayList<>();
        sequence.add("engine boom"); // 客户要求，run时候先发动引擎
        sequence.add("start"); // 启动起来
        sequence.add("stop"); // 开了一段不就停下来

        BenzBuilder benzBuilder = new BenzBuilder();
        benzBuilder.setSequence(sequence);

        // 制造出一个奔驰车
        BenzModel benz = (BenzModel) benzBuilder.getCarModel();
        // 奔驰车跑一下看看
        benz.run();

    }


}
