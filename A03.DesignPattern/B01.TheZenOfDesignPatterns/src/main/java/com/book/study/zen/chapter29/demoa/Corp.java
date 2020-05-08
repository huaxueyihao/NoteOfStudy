package com.book.study.zen.chapter29.demoa;

public abstract class Corp {

    // 如果是公司就应该
    protected abstract void produce();

    protected abstract void sell();

    public void makeMoney(){
        // 每个公司都是一样，先生产
        this.produce();
        // 然后销售
        this.sell();
    }


}
