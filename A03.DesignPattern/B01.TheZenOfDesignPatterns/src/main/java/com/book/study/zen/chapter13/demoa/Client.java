package com.book.study.zen.chapter13.demoa;

import com.book.study.zen.chapter13.AdvTemplate;

import java.util.Random;

public class Client {

    private static int MAX_COUNT = 6;

    public static void main(String[] args) {

        // 模拟发送邮件
        int i = 0;
        // 把模板定义出来，这个是从数据库中获得

        Mail mail = new Mail(new AdvTemplate());
        mail.setTail("XX银行版权所有");

        while (i < MAX_COUNT) {
            // 一下是每封邮件不同的地方
            mail.setAppellation(getRandString(5) + " 先生(女士)");
            mail.setReceiver(getRandString(5) + "@" + getRandString(8) + ".com");
            sendMail(mail);
            i++;
        }
    }

    private static void sendMail(Mail mail) {
        System.out.println("标题：" + mail.getSubject() + "\t收件人：" + mail.getReceiver() + "\t...发送成功");
    }

    private static String getRandString(int maxLength) {
        String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < maxLength; i++) {
            sb.append(source.charAt(rand.nextInt(source.length())));
        }
        return sb.toString();
    }


}
