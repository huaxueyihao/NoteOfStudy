package com.spi.test;

import com.spi.test.service.IShout;
import com.sun.tools.javac.util.ServiceLoader;

import java.util.Iterator;

public class SPIMain {

    public static void main(String[] args) {


        ServiceLoader<IShout> shouts = ServiceLoader.load(IShout.class);

        Iterator<IShout> iterator = shouts.iterator();

        while (iterator.hasNext()){
            IShout next = iterator.next();
            next.shout();
        }


    }



}
