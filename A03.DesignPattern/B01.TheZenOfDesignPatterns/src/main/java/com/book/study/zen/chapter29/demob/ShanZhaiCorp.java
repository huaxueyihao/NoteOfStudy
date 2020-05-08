package com.book.study.zen.chapter29.demob;

public class ShanZhaiCorp extends Corp {

    // 产什么产品，不知道，等被调用的才知道
    public ShanZhaiCorp(Product product) {
        super(product);
    }

    // 狂赚钱
    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("我赚钱啊...");
    }


}
