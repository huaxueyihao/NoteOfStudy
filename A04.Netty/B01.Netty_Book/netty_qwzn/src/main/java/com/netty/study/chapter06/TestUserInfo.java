package com.netty.study.chapter06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class TestUserInfo {


    public static void main(String[] args) throws IOException {
//        serializableTest();

        test();


    }

    private static void test() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("Welcome to Netty");

        int loop = 1000000;

        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();

            byte[] b = bos.toByteArray();
            bos.close();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("The jdk serializable cost time is : " + (endTime - startTime) + " ms");

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] bytes = userInfo.codeC(buffer);
        }
        endTime = System.currentTimeMillis();

        System.out.println("The byte array serializable cost time is : " + (endTime - startTime) + " ms");

    }

    private static void serializableTest() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("Welcome to Netty");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();

        System.out.println("The jdk seerializable length is : " + b.length);
        bos.close();
        System.out.println("---------------");
        System.out.println("The byte array serializable length is : " + userInfo.codeC().length);

    }

}
