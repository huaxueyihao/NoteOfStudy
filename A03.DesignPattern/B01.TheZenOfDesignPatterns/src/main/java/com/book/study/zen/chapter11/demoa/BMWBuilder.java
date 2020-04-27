package com.book.study.zen.chapter11.demoa;

import java.util.ArrayList;

public class BMWBuilder extends CarBuilder {

    private BMWModel bmw = new BMWModel();

    @Override
    public void setSequence(ArrayList<String> sequence) {
        this.bmw.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return bmw;
    }
}
