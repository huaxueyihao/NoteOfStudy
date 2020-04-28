package com.book.study.zen.chapter13.demod;

import java.util.ArrayList;

public class Thing implements Cloneable {

    // 定义一个私有变量
    private ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected Thing clone() {
        Thing thing = null;
        try {
            thing = (Thing) super.clone();
            thing.arrayList = (ArrayList<String>) this.arrayList.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return thing;
    }

    // 设置HashMap的值
    public void setValue(String value){
        this.arrayList.add(value);
    }

    // 取得arrayList的值
    public ArrayList<String> getValue(){
        return this.arrayList;
    }
}
