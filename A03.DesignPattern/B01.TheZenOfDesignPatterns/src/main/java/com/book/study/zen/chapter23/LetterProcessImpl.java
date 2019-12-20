package com.book.study.zen.chapter23;

public class LetterProcessImpl implements ILetterProcess {
    @Override
    public void writeContext(String context) {
        System.out.println("写信：" + context);
    }

    @Override
    public void fillEnvelope(String address) {
        System.out.println("地址：" + address);
    }

    @Override
    public void letterInotoEnvelope() {
        System.out.println("装入信封");
    }

    @Override
    public void sendLetter() {
        System.out.println("邮递");
    }
}
