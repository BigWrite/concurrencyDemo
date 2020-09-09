package com.mars.concurrency;

public interface Lock {

    class TimeOutException extends Exception{
        public TimeOutException(String message) {
            super(message);
        }
    }
    void lock() throws InterruptedException;
    void lock(Long mills);
    void unlock();
    int getBlockedSize();
}
