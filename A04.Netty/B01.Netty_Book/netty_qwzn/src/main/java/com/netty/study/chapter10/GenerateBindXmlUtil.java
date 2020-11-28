package com.netty.study.chapter10;


import org.jibx.binding.generator.BindGen;
import org.jibx.runtime.JiBXException;

import java.io.IOException;

public class GenerateBindXmlUtil {


    public static void main(String[] args) throws JiBXException, IOException {

        genBindFiles();

    }

    private static void genBindFiles() throws JiBXException, IOException {
        String[] args = new String[9];

        // 指定pojo源码路径(指定父包也是可以的)。必须
        args[0] = "-s";
        args[1] = "src";

        // 自定义生成binding文件名，默认文件名binding.xml。可选
        args[2] = "-b";
        args[3] = "binding.xml";

        // 打印生成过程的一些信息。可选
        args[4] = "-v";

        // 如果目录已经存在，就删除目录。可选
        args[5] = "-w";

        // -t 指定xml和xsd输出路径 路径，默认路径 (当前目录，及根目录)
        args[6] = "-t";
        args[7] = "./src/main/java/com/netty/study/chapter10/xml/pojo/order";

        // 告诉BindGen使用下面的类作为root生成bingding和schema。
        args[8] = "com.netty.study.chapter10.xml.pojo.Order";

        BindGen.main(args);



    }

}
