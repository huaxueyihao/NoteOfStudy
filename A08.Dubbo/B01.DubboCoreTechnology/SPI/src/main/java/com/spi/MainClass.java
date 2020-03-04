package com.spi;

import com.spi.demo.Animal;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MainClass {

    public static void main(String[] args) {


//        testServerLoader();

        testProtocol();


    }

    private static void testServerLoader() {

        ServiceLoader<Animal> animals = ServiceLoader.load(Animal.class);
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            animal.shout();
        }

    }

    private static void testProtocol() {

        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = (Protocol) extensionLoader.getExtension("dubbo");
        System.out.println(protocol);

    }
}
