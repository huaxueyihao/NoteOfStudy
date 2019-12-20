package com.book.study.zen.chapter23;

public class ModenPostOfficeFacade {

    private ILetterProcess letterProcess = new LetterProcessImpl();

    private Police checker = new Police();

    private ModenPostOffice modenPostOffice = new ModenPostOffice(letterProcess, checker);

    // 按照需求是否需要把letterProcess的方法，checker的方法暴露给调用者,比如,这个方法
    public void checkLetter() {
        checker.checkLetter(letterProcess);
    }

    public void sendLetter(String context, String address) {
        modenPostOffice.checkAndSendLetter(context, address);
    }


}
