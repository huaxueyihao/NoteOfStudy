package com.book.study.chapter05;

import java.util.concurrent.TimeUnit;

public class Interrupted {

    public static void main(String[] args) throws InterruptedException {

        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);

        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        TimeUnit.SECONDS.sleep(5);

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());


        Thread.sleep(20000);


    }


    static class SleepRunner implements Runnable {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class BusyRunner implements Runnable {
        public void run() {
            while (true) {

            }
        }
    }


}
