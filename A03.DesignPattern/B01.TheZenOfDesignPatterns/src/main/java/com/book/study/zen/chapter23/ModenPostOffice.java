package com.book.study.zen.chapter23;

public class ModenPostOffice {

    private ILetterProcess letterProcess = new LetterProcessImpl();

    private Police checker = new Police();

    public ModenPostOffice() {
    }

    public ModenPostOffice(ILetterProcess letterProcess, Police checker) {
        this.letterProcess = letterProcess;
        this.checker = checker;
    }

    public void sendLetter(String context, String address) {
        letterProcess.writeContext(context);
        letterProcess.fillEnvelope(address);
        letterProcess.letterInotoEnvelope();
        letterProcess.sendLetter();
    }


    public void checkAndSendLetter(String context, String address) {
        letterProcess.writeContext(context);
        checker.checkLetter(letterProcess);
        letterProcess.fillEnvelope(address);
        letterProcess.letterInotoEnvelope();
        letterProcess.sendLetter();
    }

}
