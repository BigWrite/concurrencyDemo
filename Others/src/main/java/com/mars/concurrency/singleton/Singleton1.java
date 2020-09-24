package com.mars.concurrency.singleton;

import java.util.stream.IntStream;

public class Singleton1 {

    public static Singleton1 singleton1 = new Singleton1();

    // private construct function other class could not get this instance ,unless use Singleton1.singleton1
    private Singleton1(){

    }
    public static void main(String[] args) {
        IntStream.rangeClosed(0,9).forEach(i-> new Thread(()-> System.out.println(Singleton1.singleton1), i+"").start());
    }
}
