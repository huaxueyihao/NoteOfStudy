package com.spi.sub;

import com.spi.demo.Animal;

public class Cat implements Animal {

    @Override
    public void shout() {
        System.out.println("miao miao");
    }

}
