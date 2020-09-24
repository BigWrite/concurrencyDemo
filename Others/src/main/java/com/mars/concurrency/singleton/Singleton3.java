package com.mars.concurrency.singleton;

public class Singleton3 {

    private Singleton3(){

    }
    public Singleton3 getInstance(){
        return InnerClass.singleton3;
    }
    private static class InnerClass{
        private final static Singleton3 singleton3= new Singleton3();
    }
}
