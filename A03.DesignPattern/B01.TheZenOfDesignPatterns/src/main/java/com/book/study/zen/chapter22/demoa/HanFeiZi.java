package com.book.study.zen.chapter22.demoa;

public class HanFeiZi implements IHanFeiZi {

    // 韩非子是否在吃饭，微微监控的判断标准
    private volatile boolean isHavingBreakfast = false;
    // 韩非子是否在娱乐
    private volatile boolean isHavingFun = false;

    // 韩非子要吃饭了
    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:开始吃早餐了...");
        this.isHavingBreakfast = true;
    }


    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        this.isHavingFun = true;
    }

    public boolean isHavingFun(){
        return isHavingFun;
    }

    public void setHavingFun(boolean isHavingFun){
        this.isHavingFun = isHavingFun;
    }

    public boolean isHavingBreakfast(){
        return isHavingBreakfast;
    }

    public void setHavingBreakfast(boolean isHavingBreakfast){
        this.isHavingBreakfast = isHavingBreakfast;
    }

}
