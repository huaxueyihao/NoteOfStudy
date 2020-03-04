package com.spi.test.animal;

import com.spi.test.service.IShout;

public class Dog implements IShout {

    public void shout() {
        System.out.println("wang wang");
    }
}
