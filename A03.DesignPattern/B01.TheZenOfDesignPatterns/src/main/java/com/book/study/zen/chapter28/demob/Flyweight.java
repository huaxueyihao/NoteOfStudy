package com.book.study.zen.chapter28.demob;

public abstract class Flyweight {

    // 内部状态
    private String intrinsic;

    // 外部状态
    protected final String Extrinsic;

    public Flyweight(String extrinsic) {
        Extrinsic = extrinsic;
    }

    // 定义业务操作
    public abstract void operate();

    // 内部状态的getter/setter
    public String getIntrinsic(){
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic){
        this.intrinsic = intrinsic;
    }


}
