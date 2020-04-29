package com.book.study.zen.chapter16.demob;

import com.book.study.zen.chapter16.IWomen;

public class Son extends Handler {

    public Son() {
        super(Handler.SON_LEVEL_REQUEST);
    }

    @Override
    protected void response(IWomen women) {
        System.out.println("--------妻子向儿子请示--------");
        System.out.println(women.getRequest());
        System.out.println("儿子的答复是：同意\n");
    }
}
