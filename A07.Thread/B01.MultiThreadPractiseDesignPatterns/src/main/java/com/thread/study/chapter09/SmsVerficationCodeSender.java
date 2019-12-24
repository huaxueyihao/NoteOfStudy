package com.thread.study.chapter09;

import java.util.concurrent.*;

public class SmsVerficationCodeSender {
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "VerfCodeSender");
            t.setDaemon(true);
            return t;
        }
    }, new ThreadPoolExecutor.DiscardPolicy());


    public void sendVerificationSms(final String msisdn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // 生成随机数验证码
//                int verificationCode = ThreadS

            }
        };

        EXECUTOR.submit(task);
    }

}
