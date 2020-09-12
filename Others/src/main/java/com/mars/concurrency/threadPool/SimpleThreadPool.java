package com.mars.concurrency.threadPool;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleThreadPool {
    private static final int DEFAULT_SIZE = 10;
    private final int size;
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private static final String NAME_PRE = "ZZZZ-";
    private static int seq = 0;
    public static final ThreadGroup group = new ThreadGroup("group");
    public static final List<Work> WORK_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private enum ThreadState {
        FREE, BLOCKED, DEAD, RUNNING
    }

    private void init() {
        IntStream.range(0, size).forEach(i -> {
            createWork("");
        });
    }

    public void createWork(String name) {
        Work work = new Work(group, NAME_PRE + name + seq++);
        work.start();
        WORK_QUEUE.add(work);

    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.add(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private static class Work extends Thread {
        private volatile ThreadState threadState = ThreadState.FREE;

        public ThreadState getThreadState() {
            return threadState;
        }

        public void close() {
            this.threadState = ThreadState.DEAD;
        }

        private Work(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTERQ:
            while (this.threadState != ThreadState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            threadState = ThreadState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTERQ;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    threadState = ThreadState.RUNNING;
                    runnable.run();
                    threadState = ThreadState.FREE;
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.rangeClosed(1, 40).forEach(i -> threadPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "running");
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end");
        }));

    }


}
