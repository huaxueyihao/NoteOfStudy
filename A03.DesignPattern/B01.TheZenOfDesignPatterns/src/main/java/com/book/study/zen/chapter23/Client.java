package com.book.study.zen.chapter23;

import org.junit.Test;


public class Client {


    public static void main(String[] args) {
        // 不使用门面
        //testUnFacade();
        // 使用门面
        //testFacade();
        // 发送信的业务需要调整，要修改门面，信件发送之前，增加检查的步骤
        // testFacade2();

        // 再次调整后的门面,这样调整后，此时ModenPostOfficeFacade是门面，业务逻辑有调整只需要调整ModenPostOffice的类
        // 而不需要调整ModenPostOfficeFacade
        testFacade3();


    }

    private static void testFacade3() {
        ModenPostOfficeFacade modenPostOfficeFacade = new ModenPostOfficeFacade();
        modenPostOfficeFacade.sendLetter("尊敬的......", "中国浙江....");
    }


    /**
     * 业务逻辑修改之后导致，需要调整门面的方法的业务逻辑
     * 不符合开闭原则，对修改关闭，对扩展开放
     */
    private static void testFacade2() {
        ModenPostOffice modenPostOffice = new ModenPostOffice();
        modenPostOffice.checkAndSendLetter("尊敬的......", "中国浙江....");
    }

    /**
     * 使用门面，需要一个包装类，将写信，装信，发送封装起来
     * 客户端不再关系，处理信件的业务顺序问题，实际动作还是委托给了ILetterProcess完成的
     */
    private static void testFacade() {
//        ModenPostOffice modenPostOffice = new ModenPostOffice();
//        modenPostOffice.sendLetter("尊敬的......", "中国浙江....");
    }

    /**
     * 一般情况下，客户端调用：需要自己按业务顺序调用多个方法
     */
    public static void testUnFacade() {
        LetterProcessImpl letterProcess = new LetterProcessImpl();
        letterProcess.writeContext("尊敬的......");
        letterProcess.fillEnvelope("中国浙江....");
        letterProcess.letterInotoEnvelope();
        letterProcess.sendLetter();
    }


}
