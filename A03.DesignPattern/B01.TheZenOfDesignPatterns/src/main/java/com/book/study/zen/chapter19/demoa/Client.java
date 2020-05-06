package com.book.study.zen.chapter19.demoa;

public class Client {

    public static void main(String[] args) {

        // 没有与外系统连接的时候，是这样写的
        IUserInfo yourGirl = new UserInfo();
        // 从数据库中查到101个
        for (int i = 0; i < 101; i++) {
            yourGirl.getMobileNumber();
        }

        //


    }


}
