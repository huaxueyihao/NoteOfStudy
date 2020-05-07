package com.book.study.zen.chapter22.demob;

import com.book.study.zen.chapter22.demoa.IHanFeiZi;
import com.book.study.zen.chapter22.demoa.ILiSi;
import com.book.study.zen.chapter22.demoa.LiSi;

public class HanFeiZi implements IHanFeiZi {

    // 把李斯声明出来
    private ILiSi liSi = new LiSi();

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子：开始吃饭了...");
        // 通知李斯
        this.liSi.update("韩非子在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        // 通知李斯
        this.liSi.update("韩非子在娱乐");
    }
}
