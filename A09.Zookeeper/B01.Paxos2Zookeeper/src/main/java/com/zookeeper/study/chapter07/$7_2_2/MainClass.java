package com.zookeeper.study.chapter07.$7_2_2;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.BinaryOutputArchive;
import org.apache.zookeeper.server.ByteBufferInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 1.实体类需要实现Record接口的serialize和deserialize方法
 * 2.构建一个序列化器BinaryOutputArchive
 * 3.序列化。调用实体类的serialize方法，将对象序列化到指定tag中取。
 * 4.反序列化：从指定的tag中反序列化出数据内容
 *
 *
 */
public class MainClass {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);

        new MockReqHeader(0x34221ecccb92a4el,"ping").serialize(boa,"header");

        ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
        ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
        BinaryInputArchive bbia = BinaryInputArchive.getArchive(bbis);

        MockReqHeader header2 = new MockReqHeader();
        header2.deserialize(bbia,"header");

        bbis.close();
        baos.close();



    }
}
