package com.book.study.zen.chapter09;

public class MaleFactory implements HumanFactory {


    @Override
    public Human createYellowHuman() {
        return new MaleBlackHuman();
    }

    @Override
    public Human createWhiteHuman() {
        return new MaleWhiteHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new MaleYellowHuman();
    }
}
