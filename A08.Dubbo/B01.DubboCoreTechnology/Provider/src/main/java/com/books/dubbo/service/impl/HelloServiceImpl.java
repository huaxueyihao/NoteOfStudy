package com.books.dubbo.service.impl;

import com.books.dubbo.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String speakHello(String name) {


        return "你好:" + name;
    }
}
