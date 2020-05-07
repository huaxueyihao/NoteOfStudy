package com.book.study.zen.chapter22.demoa;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        // 定义出韩非子和李斯
        LiSi liSi = new LiSi();
        HanFeiZi hanFeiZi = new HanFeiZi();

        // 观察早餐
        Watch watchBreakfast = new Watch(hanFeiZi,liSi,"breakfast");
        // 开始启动线程，监控
        watchBreakfast.start();
        // 观察娱乐情况
        Watch watchFun = new Watch(hanFeiZi,liSi,"fun");
        watchFun.start();
        // 然后我们看看韩非子在干什么
        Thread.sleep(1000);
        hanFeiZi.haveBreakfast();
        Thread.sleep(1000);
        hanFeiZi.haveFun();

    }


}
