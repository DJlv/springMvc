package com.demo.dxc.demo00005;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrencyTest {
    private static final long count = 100001;

    public static void main(String[] args) {

        concurrency(); //concurrency ---------1733
        serial(); // serial ---------1032
    }

    private static void concurrency() {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a= 0;
                for (int i = 0; i < count; i++) {
                    a+=5;
                    log.info("{}",a);

                }
            }
        },"thread--0");
        thread.start();
        long b = 0;
        for (int i = 0; i < count; i++) {
            b--;
            log.info("{}",b);

        }
        long time = System.currentTimeMillis() - start;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("concurrency ---------{}",time);

    }


    private static void serial() {
        long start =System.currentTimeMillis();
        int a= 0;
        for (int i = 0; i <count; i++) {
            a+=5;
            log.info("{}",a);
        }
        int b=0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        log.info("serial ---------{}",time);

    }
}
