package com.demo.thread.oneDemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo001 {
    public static void main(String[] args) {
        // 创建线程对象
        Thread t =new Thread() {

            @Override
            public void run() {
                log.info("创建线程-->t");
            }
        };
        // 启动线程
        t.start();
        log.info("线程-->main");

    }
}
