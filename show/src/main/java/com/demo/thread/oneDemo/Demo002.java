package com.demo.thread.oneDemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo002 {
    public static void main(String[] args) {
        Runnable runnable1 = () -> {
                log.info("Runable 线程1");
        };
        Runnable runnable2 = new Runnable() {

            @Override
            public void run() {
                log.info("Runable 线程2");
            }
        };

        Thread thread1 = new Thread(runnable1,"t1");
        Thread thread2 = new Thread(runnable2,"t2");
        thread1.start();
        thread2.start();
    }
}
