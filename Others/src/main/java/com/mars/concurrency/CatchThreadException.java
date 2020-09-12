package com.mars.concurrency;

public class CatchThreadException {

    public static void main(String[] args) {
        int A = 0;
        int B = 10;
        Thread thread = new Thread(() -> {
            int a = B / A;
        });
        thread.start();
        /*thread.setUncaughtExceptionHandler((thread1,e)->{
            System.out.println(e);
            System.out.println(thread1);
        });*/
        System.out.println(thread.getUncaughtExceptionHandler());
        System.out.println("END");
    }
}
