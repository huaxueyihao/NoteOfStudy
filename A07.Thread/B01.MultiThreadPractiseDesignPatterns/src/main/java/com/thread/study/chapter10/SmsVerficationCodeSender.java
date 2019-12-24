package com.thread.study.chapter10;

import java.text.DecimalFormat;
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


    public static void main(String[] args) {

        SmsVerficationCodeSender client = new SmsVerficationCodeSender();
        client.sendVerificationSms("18912345678");
        client.sendVerificationSms("18912345678");
        client.sendVerificationSms("18912345678");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            ;
        }


    }

    private void sendVerificationSms(String msisdn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                int verificationCode = ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
                DecimalFormat df = new DecimalFormat("000000");
                String txtVerCode = df.format(verificationCode);

                // 发送验证码短信
                sendSms(msisdn, txtVerCode);
            }
        };
        EXECUTOR.submit(task);
    }


    private void sendSms(String msisdn, String verificationCode) {
        System.out.println("Sending verification code " + verificationCode + " to " + msisdn);
    }


}
