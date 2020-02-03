package com.datastructure.tree.huffmancode;

import java.io.*;
import java.util.*;

public class HuffmanCode {


    public static void main(String[] args) {
//        String str = "i like like like java do you like a java";
//        byte[] contentBytes = str.getBytes();
//        System.out.println(contentBytes.length);
//
//        List<Node> nodes = getNodes(contentBytes);
//        System.out.println(nodes);
//
//        Node root = createHuffmanTree(nodes);
//
//        preOrder(root);
//
//
//        getCodes(root, "", stringBuilder);
//        System.out.println("生成赫夫曼编码表：" + huffmanCodes);
//
//        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
//        System.out.println("huffmanCodeBytes=" + Arrays.toString(huffmanCodeBytes));
//
//
//        byte[] decode = decode(huffmanCodes, huffmanCodeBytes);
//        System.out.println(new String(decode));

        String srcFile = "/Users/amao/Documents/picture/me/IMG_20200104_174250.jpg";
        String dstFile = "/Users/amao/Documents/picture/me/IMG_20200104_174250.zip";

        zipFile(srcFile, dstFile);

    }


    // 编写一个方法，完成对压缩文件的解压

    /**
     * @param zipFile
     * @param dstFile
     */
    public static void unZipFile(String zipFile, String dstFile) {

        InputStream is = null;
        // 定义对象输入流
        ObjectInputStream ois = null;
        // 定义文件的输出流
        OutputStream os = null;

        try {
            // 创建文件输入流
            is = new FileInputStream(zipFile);
            // 创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            // 读取byte数组，huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 多去赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            //

            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            os = new FileOutputStream(dstFile);

            os.write(bytes);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static void zipFile(String srcFile, String dstFile) {


        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        // 创建文件的输入流
        FileInputStream is = null;
        try {
            is = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的byte[]
            byte[] b = new byte[is.available()];

            // 读取文件
            is.read(b);
            // 获取到文件对应的赫夫曼编码表
            byte[] huffmanBytes = huffmanzip(b);
            // 创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            // 创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);

            oos.writeObject(huffmanBytes);
            // 这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们回复源文件时使用
            // 注意一定要把赫夫曼编码 写入压缩文件
            oos.writeObject(huffmanCodes);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 编写一个方法，完成对压缩数据的解码
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {

        // 1. 先得到huffmanBytes 对应的二进制的字符串，形式1010100010111...
        StringBuilder stringBuilder = new StringBuilder();
        // 将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(flag, huffmanBytes[i]));
        }
        System.out.println("赫夫曼字节数组对应的二进制字符串=" + stringBuilder.toString());

        // 把字符串按照指定的赫夫曼编码进行解码
        // 把赫夫曼编码表进行调换，因为反向查询97->100

        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        System.out.println("map=" + map);

        // 创建要给集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;

            while (flag) {
                // 取出一个字符'1' '0'
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);

                if (b == null) {
                    count++;
                } else {
                    // 匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }

        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }


    // 1.将huffmanCodeBytes重新转成赫夫曼对应的二进制的字符串"1010100010111.。。"
    // 2.赫夫曼编码对应的二进制的字符串"10100010111..." =》对照 赫夫曼编码
    private static String byteToBitString(boolean flag, byte b) {
        // 使用变量保存b
        int temp = b;

        if (flag) {
            temp |= 256;
        }

        String str = Integer.toBinaryString(temp);
        System.out.println("str=" + str);

        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }

    }

    private static byte[] huffmanzip(byte[] bytes) {

        List<Node> nodes = getNodes(bytes);
        // 根据nodes创建霍夫曼编码
        Node root = createHuffmanTree(nodes);
        // 对应的赫夫曼编码(根据 赫夫曼树)

        getCodes(root, "", stringBuilder);

        //
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }


    // 编写一个方法，将字符串对应的byte[] 数组，通过生成的何驸马编码表，返回一个赫夫曼压缩后的byte[]
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {

        // 1. 利用huffmanCodes将bytes 转成赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //

        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }


        System.out.println("测试stringBuilder=" + stringBuilder);

        // 将"10101000101111111...."转成byte

        int len;

        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }

        // 穿件 存储压缩后的byte 数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            // 将strByte转成一个byte放入到byte[]中
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }

        return huffmanCodeBytes;
    }


    // 生成赫夫曼对应的赫夫曼编码表
    // 1. 将赫夫曼编码表存放在Map<Byte,String> 形式
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    // 2.在生成赫夫曼编码表时，需要去凭借路径，定义一个StringBuilder 存储某个叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();


    public static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }

        getCodes(root.left, "0", stringBuilder);

        getCodes(root.right, "1", stringBuilder);

        return huffmanCodes;

    }


    /**
     * @param node          传入结点
     * @param code          路径：左子结点是0，右子结点1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        // 将code加入到stringBuilder
        stringBuilder2.append(code);
        if (node != null) {
            if (node.data == null) {
                // 递归处理
                // 向左
                getCodes(node.left, "0", stringBuilder2);
                // 向右
                getCodes(node.right, "1", stringBuilder2);
            } else {
                // 表示找到某个叶子结点的最后
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }


    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("赫夫曼树为空");
        }
    }

    private static Node createHuffmanTree(List<Node> nodes) {

        while (nodes.size() > 1) {
            Collections.sort(nodes);

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            nodes.remove(leftNode);
            nodes.remove(rightNode);

            nodes.add(parent);

        }

        return nodes.get(0);
    }

    private static List<Node> getNodes(byte[] bytes) {

        ArrayList<Node> nodes = new ArrayList<>();

        // 存储每一个byte出现的次数
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 把每个键值对转成一个Node对象，并加入到nodes集合

        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
//        Collections.sort(nodes);
        return nodes;
    }


}


class Node implements Comparable<Node> {

    Byte data; // 存放数据本身，比如'a' =>97
    int weight; // 权值，表示字符数据出现的次数

    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }


    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    public void preOrder() {
        System.out.println(this);

        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }

    }


}
