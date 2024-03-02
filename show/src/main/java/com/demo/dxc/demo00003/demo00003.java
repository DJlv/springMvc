package com.demo.dxc.demo00003;

import com.demo.itcast.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class demo00003 {
    public static void main(String[] args) {
        String x = "thread_0";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Sleeper.sleep(1000);
                }
            }
        },x);
        log.info("开始线程{}",x);
        Sleeper.sleep(2);

        thread.interrupt();
        log.info("打断线程{}",x);
    }
}
