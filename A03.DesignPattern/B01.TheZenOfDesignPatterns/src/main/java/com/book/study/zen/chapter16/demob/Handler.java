package com.book.study.zen.chapter16.demob;

import com.book.study.zen.chapter16.IWomen;

public abstract class Handler {

    public final static int FATHER_LEVEL_REQUEST = 1;

    public final static int HUSBAND_LEVEL_REQUEST = 2;

    public final static int SON_LEVEL_REQUEST = 3;

    private int level = 0;

    private Handler nextHandler;

    public Handler(int level) {
        this.level = level;
    }

    public final void HandleMessage(IWomen women) {
        if (women.getType() == this.level) {
            this.response(women);
        } else {
            if (this.nextHandler != null) {
                this.nextHandler.HandleMessage(women);
            } else {
                System.out.println("-----没地方请示了，按不同意处理--------\n");
            }
        }
    }

    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }


    protected abstract void response(IWomen women);


}
