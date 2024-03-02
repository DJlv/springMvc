package com.demo.dxc.demo00008;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class demo00008 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread--00");

        Thread thread = Thread.currentThread();
        log.info("{}",thread);
        Integer i1 =100;
        Integer i2 =100;
        Integer i5 =101;
        Integer i6 =101;
        Integer i3 =300;
        Integer i4 =300;
        log.info("{}",i1==i2);
        log.info("{}",i3==i4);
        log.info("{}",i5==i6);
    }
}
