package com.thread.study.chapter08;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomScheduler implements Runnable {

    private LinkedBlockingQueue<Runnable> activationQueue = new LinkedBlockingQueue<>();


    @Override
    public void run() {

        dispatch();

    }

    public <T> Future<T> enqueue(Callable<T> methodRequest) {
        final FutureTask<T> task = new FutureTask<T>(methodRequest) {

            @Override
            public void run() {
                try {
                    super.run();
                    // 捕获所有可能抛出的对象T，避免该任务运行失败而导致其所在的线程终止
                } catch (Throwable t) {
                    this.setException(t);
                }
            }
        };

        try {
            activationQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return task;
    }

    public void dispatch() {
        while (true) {
            Runnable methodRequest;
            try {
                methodRequest = activationQueue.take();

                // 防止个别任务执行失败导致线程终止的代码在run方法中
                methodRequest.run();
            } catch (InterruptedException e) {
                // 处理该异常
            }
        }
    }
}
