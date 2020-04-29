package com.book.study.zen.chapter16.demoa;

import com.book.study.zen.chapter16.IWomen;

public class Son implements IHandler {


    @Override
    public void HandlerMessage(IWomen women) {
        System.out.println("母亲的请示：" + women.getRequest());
        System.out.println("儿子的答复是：同意");
    }
}
