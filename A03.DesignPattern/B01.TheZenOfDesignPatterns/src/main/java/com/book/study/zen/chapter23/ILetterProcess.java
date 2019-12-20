package com.book.study.zen.chapter23;

public interface ILetterProcess {

    // 首先要写信的内容
    void writeContext(String context);

    // 写信封
    void fillEnvelope(String address);

    // 把信放在信封里
    void letterInotoEnvelope();

    // 邮递
    void sendLetter();


}
