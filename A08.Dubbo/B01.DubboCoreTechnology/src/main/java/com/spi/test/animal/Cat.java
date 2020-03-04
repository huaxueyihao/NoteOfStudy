package com.spi.test.animal;

import com.spi.test.service.IShout;

public class Cat implements IShout {

    public void shout() {
        System.out.println("miao miao");
    }
}
