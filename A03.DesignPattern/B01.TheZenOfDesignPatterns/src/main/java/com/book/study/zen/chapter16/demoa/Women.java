package com.book.study.zen.chapter16.demoa;

import com.book.study.zen.chapter16.IWomen;

public class Women implements IWomen {

    /**
     * 通过一个int类型的参数来描述妇女的个人状态
     * 1--未出嫁
     * 2--出嫁
     * 3--夫死
     *
     * @return
     */
    private int type = 0;

    // 构造函数传递过来请求
    private String request = "";

    // 构造函数传递过来请求
    public Women(int type, String request) {
        this.type = type;
        this.request = request;
    }

    // 获得自己的状况
    @Override
    public int getType() {
        return this.type;
    }

    // 获得服务的请求
    @Override
    public String getRequest() {
        return this.request;
    }


}
