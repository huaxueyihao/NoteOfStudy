package com.book.study.zen.chapter22.demoa;

public class Watch extends Thread {

    private HanFeiZi hanFeiZi;

    private LiSi liSi;

    private String type;

    public Watch(HanFeiZi hanFeiZi, LiSi liSi, String type) {
        this.hanFeiZi = hanFeiZi;
        this.liSi = liSi;
        this.type = type;
    }

    @Override
    public void run() {
        while (true) {
            if (this.type.equals("breakfast")) {
                // 如果发现韩非子在吃饭，就通知李斯
                if (this.hanFeiZi.isHavingBreakfast()) {
                    this.liSi.update("韩非子在吃饭");
                    // 重置状态，继续监控
                    this.hanFeiZi.setHavingBreakfast(false);
                }
            } else {
                // 监控是否在娱乐
                if (this.hanFeiZi.isHavingFun()) {
                    this.liSi.update("韩非子在娱乐");
                    this.hanFeiZi.setHavingFun(false);
                }
            }
        }
    }
}
