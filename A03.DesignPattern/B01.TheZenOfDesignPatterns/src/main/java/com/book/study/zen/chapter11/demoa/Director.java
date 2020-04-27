package com.book.study.zen.chapter11.demoa;

import java.util.ArrayList;

public class Director {

    private ArrayList<String> sequence = new ArrayList<>();

    private BenzBuilder benzBuilder = new BenzBuilder();

    private BMWBuilder bmwBuilder = new BMWBuilder();


    /**
     * A类型的奔驰车模型，先start，然后stop，其他什么引擎、喇叭一概没有
     * @return
     */
    public BenzModel getABenzModel(){
        // 清理场景
        this.sequence.clear();
        // ABenzModel的执行顺序
        this.sequence.add("start");
        this.sequence.add("stop");
        // 按照顺序返回一个奔驰车
        this.benzBuilder.setSequence(this.sequence);
        return (BenzModel) this.benzBuilder.getCarModel();
    }

    /**
     * B类型的奔驰车模型，先发动引擎，然后启动，然后停止，没有喇叭
     * @return
     */
    public BenzModel getBBenzModel(){
        this.sequence.clear();
        this.sequence.add("engine boom");
        this.sequence.add("start");
        this.sequence.add("stop");
        this.benzBuilder.setSequence(this.sequence);
        return (BenzModel) this.benzBuilder.getCarModel();
    }

    /**
     * C型号的宝马车先按下喇叭，然后启动，然后停止
     * @return
     */
    public BMWModel getCBMWModel(){
        this.sequence.clear();
        this.sequence.add("alarm");
        this.sequence.add("start");
        this.sequence.add("stop");
        this.bmwBuilder.setSequence(this.sequence);
        return (BMWModel) this.bmwBuilder.getCarModel();
    }

    /**
     * D类型的宝马车只有一个功能，就是跑，启动起来就跑，永远不停止
     */
    public BMWModel getDBMWModel(){
        this.sequence.clear();
        this.sequence.add("start");
        this.bmwBuilder.setSequence(this.sequence);
        return (BMWModel) this.benzBuilder.getCarModel();
    }

}
