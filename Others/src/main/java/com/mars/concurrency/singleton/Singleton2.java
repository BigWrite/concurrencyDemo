package com.mars.concurrency.singleton;

import java.util.stream.IntStream;

public class Singleton2 {
    private static volatile Singleton2 singleton2;

    // private construct function other class could not get this instance ,unless use Singleton2.getSingleton2
    private Singleton2(){

    }

    //double check and volatile avoid jvm optimization
    public static Singleton2 getSingleton2() {
        if (singleton2 == null) {
            synchronized (Singleton2.class){
                if (singleton2 == null) {
                    singleton2 = new Singleton2();
                }
            }
        }
        return Singleton2.singleton2;
    }


    public static void main(String[] args) {
        IntStream.rangeClosed(0,9).forEach(i-> {
            new Thread(() -> {
                System.out.println(Singleton2.getSingleton2());

            }, i + "").start();
        });
    }

}
