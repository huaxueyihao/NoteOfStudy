package com.spi.sub;

import com.spi.demo.Animal;

public class Dog implements Animal {

    @Override
    public void shout() {
        System.out.println("wang wang");
    }


}
