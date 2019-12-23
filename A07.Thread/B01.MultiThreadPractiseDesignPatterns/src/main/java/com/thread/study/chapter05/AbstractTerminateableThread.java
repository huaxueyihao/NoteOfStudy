package com.thread.study.chapter05;

public abstract class AbstractTerminateableThread extends Thread implements Terminatable {

    // 模式角色：Two-phaseTermination.TerminationToken
    public final TerminationToken terminationToken;

    public AbstractTerminateableThread() {
        this(new TerminationToken());
    }

    public AbstractTerminateableThread(TerminationToken terminationToken) {
        super();
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    /**
     * 留给子类实现其线程处理逻辑
     *
     * @throws Exception
     */
    protected abstract void doRun() throws Exception;

    protected void doCleanup(Exception cause) {
        // 什么也不做
    }

    protected void doTerminiate() {
        // 什么也不做
    }


    @Override
    public void run() {
        Exception ex = null;
        try {
            for (; ; ) {
                // 在执行线程的处理逻辑前先判断线程停止的标志
                if (terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0) {
                    break;
                }
                doRun();
            }
        } catch (Exception e) {
            // 是的线程能够响应interrupt调用而退出
            ex = e;
        } finally {
            try {
                doCleanup(ex);
            } finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    /**
     * 完成线程停止的准备阶段
     *
     */
    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminiate();
        }finally {
            // 若无待处理的任务，则试图强制终止线程
            if(terminationToken.reservations.get() <= 0){
                super.interrupt();
            }
        }
    }

    public void terminate(boolean waitUtilThreadTerminated){
        terminate();
        if(waitUtilThreadTerminated){
            try {
                this.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
