package com.book.study.zen.chapter19.demoa;

public class UserInfo implements IUserInfo {


    /**
     * 获得家庭地址，下属送礼也可以找到地方
     * @return
     */
    @Override
    public String getUserName() {
        System.out.println("姓名叫做....");
        return null;
    }

    @Override
    public String getHomeAddress() {
        System.out.println("员工的家庭地址是...");
        return null;
    }

    @Override
    public String getMobileNumber() {
        System.out.println("员工的手机号是...");
        return null;
    }

    @Override
    public String getOfficeTelNumber() {
        System.out.println("办公司电话是...");
        return null;
    }

    @Override
    public String getJobPosition() {
        System.out.println("这个人的职位是BOSS...");
        return null;
    }

    @Override
    public String getHomeTelNumber() {
        System.out.println("员工的家庭电话是...");
        return null;
    }
}
