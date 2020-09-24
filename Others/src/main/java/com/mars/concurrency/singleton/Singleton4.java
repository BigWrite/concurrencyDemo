package com.mars.concurrency.singleton;

public class Singleton4 {

    private Singleton4(){

    }

    public static void main(String[] args) {
        InstanceEnum.ENUM.getSingleton4();
    }

    private enum InstanceEnum{
        ENUM;
        private final Singleton4 singleton4;
        InstanceEnum(){
            singleton4 = new Singleton4();
        }
        public Singleton4 getSingleton4(){
            return singleton4;
        }
    }
}
