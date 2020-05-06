package com.book.study.zen.chapter19.demoa;

public interface IUserInfo {

    // 获得用户姓名
    public String getUserName();

    // 获得家庭地址
    public String getHomeAddress();

    // 手机号码，这个太重要，手机泛滥
    public String getMobileNumber();


    public String getOfficeTelNumber();

    // 这个人的职位是什么
    public String getJobPosition();

    // 获得家庭电话，这有点不好，我不喜欢大家庭打电话讨论工作
    public String getHomeTelNumber();

}
