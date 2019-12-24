package com.thread.study.chapter08;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

// 模式角色：ActiveObject.Proxy
public class AsyncRequestPersistence implements RequestPersistence {

    private static final long ONE_MINUTE_IN_SECONDS = 60;

    private final AtomicLong taskTimeConsumedPerInterval = new AtomicLong(0);
    private final AtomicLong requestSubmittedPerIterval = new AtomicLong(0);

    // 模式角色：ActiveObject.Servant
    private final DiskbasedRequestPersistence delegate = new DiskbasedRequestPersistence();

    // 模式角色:ActiveObject.Scheduler
    private final ThreadPoolExecutor scheduler;

    // 用于保存AsyncRequestPErsistence的唯一实例
    private static class InstanceHolder {
        final static RequestPersistence INSTANCE = new AsyncRequestPersistence();
    }


    // 私有构造器
    private AsyncRequestPersistence() {
        scheduler = new ThreadPoolExecutor(1, 3, 60 * ONE_MINUTE_IN_SECONDS, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t;
                t = new Thread(r, "AsyncRequestPersistence");
                return t;
            }
        });

        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 启动对列监控定时任务
        Timer montitorTimer = new Timer(true);
        montitorTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                System.out.println("task count : " + requestSubmittedPerIterval + ",Queue size :" + scheduler.getQueue().size() + ", taskTimeConsumedPerInterval : " + taskTimeConsumedPerInterval.get() + " ms");

                taskTimeConsumedPerInterval.set(0);
                requestSubmittedPerIterval.set(0);
            }
        }, 0, ONE_MINUTE_IN_SECONDS * 1000);
    }

    public static RequestPersistence getInstance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public void store(MMSDeliveryRequest request) {
        /**
         * 将对store方法的调用封装成MethodRequest对象，并存入缓冲区
         */
        // 模式角色：ActiveObject.MethodRequest
        Callable<Boolean> methodRequest = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long start = System.currentTimeMillis();
                try {
                    delegate.store(request);
                } finally {
                    taskTimeConsumedPerInterval.addAndGet(System.currentTimeMillis() - start);
                }
                return Boolean.TRUE;
            }
        };

        scheduler.submit(methodRequest);
        requestSubmittedPerIterval.incrementAndGet();
    }


}
