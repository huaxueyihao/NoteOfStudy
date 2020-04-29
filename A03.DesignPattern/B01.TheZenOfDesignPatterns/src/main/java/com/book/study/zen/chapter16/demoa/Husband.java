package com.book.study.zen.chapter16.demoa;

import com.book.study.zen.chapter16.IWomen;

public class Husband implements IHandler {


    // 妻子向丈夫请示
    @Override
    public void HandlerMessage(IWomen women) {
        System.out.println("妻子的请示是：" + women.getRequest());
        System.out.println("丈夫的答复是：同意");

    }
}
