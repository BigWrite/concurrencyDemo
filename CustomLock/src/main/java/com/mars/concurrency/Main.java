package com.mars.concurrency;

import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    private static final BooleanLock booleanLock = new BooleanLock();

    public static void main(String[] args) {
        Stream.of("T1", "T2", "T3").map(Main::createThread).forEach(thread -> thread.start());
    }

    public static Thread createThread(String name) {
        return new Thread(() -> {
            try {
                booleanLock.lock();
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                booleanLock.unlock();
            }
        }, name);
    }

    public static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working").ifPresent(System.out::println);
        Thread.sleep(1000);
    }
}
