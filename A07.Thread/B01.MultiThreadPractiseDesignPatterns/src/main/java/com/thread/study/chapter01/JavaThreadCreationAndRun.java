package com.thread.study.chapter01;

public class JavaThreadCreationAndRun {


    public static void main(String[] args) {

        System.out.println("The main method was executed by thread: " + Thread.currentThread().getName());

        Thread thread = new Thread(new Helper("Java Thread Anywhere"));
        thread.setName("A-Worker-Thread");
        thread.start();


    }


    static class Helper implements Runnable {

        final String message;

        public Helper(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            while (true)
                doSomething(message);
        }

        private void doSomething(String message) {
            System.out.println("The doSomething method was executed by thread: " + Thread.currentThread().getName());
            System.out.println("Do Something with " + message);
        }
    }


}
