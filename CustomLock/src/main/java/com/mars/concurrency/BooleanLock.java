package com.mars.concurrency;

import java.util.*;

public class BooleanLock implements Lock {


    // when flag is false,this Lock is hold on other thread. when flag is true,this Thread can get Lock
    private Boolean locked;
    private Collection<Thread> blockedThreads = new HashSet<>();
    private Thread currentThread;

    public BooleanLock(){
        this.locked = true;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (!locked) {
            blockedThreads.add(Thread.currentThread());
            Optional.of(Thread.currentThread().getName() + " is Locked.LockSize:"+this.blockedThreads.size()).ifPresent(System.out::println);
            this.wait();
        }
        Optional.of(Thread.currentThread().getName() + " get Lock").ifPresent(System.out::println);
        this.locked = false;
        blockedThreads.remove(Thread.currentThread());
        this.currentThread = Thread.currentThread();
    }

    @Override
    public void lock(Long mills) {

    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread() == currentThread) {
            Optional.of(Thread.currentThread().getName() + " unlock").ifPresent(System.out::println);
            this.locked = true;
            blockedThreads.remove(Thread.currentThread());
            this.notifyAll();
        }
    }

    @Override
    public int getBlockedSize() {
        return Collections.unmodifiableCollection(blockedThreads).size();
    }
}
