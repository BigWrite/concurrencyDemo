package com.mars.concurrency;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class ProduceAndConsumer {

    private static final Object LOCK = new Object();
    private static volatile int index = 0;
    private static boolean changed = false;


    public static void main(String[] args) {
        ProduceAndConsumer pc = new ProduceAndConsumer();
        new Thread("P1") {
            @Override
            public void run() {
                pc.produce();
            }
        }.start();
        new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }, "P2").start();
        new Thread(() -> {
            while (true) {
                pc.produce();
            }
        }, "P3").start();
        new Thread(() -> {
            while (true) {
                pc.consumer();
            }
        }, "c1").start();
        new Thread(() -> {
            while (true) {
                pc.consumer();
            }
        }, "c2").start();
        Stream.of("c3","c4").forEach(name->{
            new Thread(() -> {
                while (true) {
                    pc.consumer();
                }
            }, name).start();
        });
        Stream.of("p4","p5","p6").forEach(name->{
            new Thread(() -> {
                while (true) {
                    pc.produce();
                }
            }, name).start();
        });
    }

    public void produce() {
        synchronized (LOCK) {
            if (!changed) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Optional.of(Thread.currentThread().getName() + "-{P}->" + ++index).ifPresent(System.out::println);
                changed = false;
                LOCK.notifyAll();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consumer() {
        synchronized (LOCK) {
            if (!changed) {
                Optional.of(Thread.currentThread().getName() + "-{C}->" + index).ifPresent(System.out::println);
                changed = true;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOCK.notifyAll();
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
