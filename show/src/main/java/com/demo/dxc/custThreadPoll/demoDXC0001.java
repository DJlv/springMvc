package com.demo.dxc.custThreadPoll;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class demoDXC0001 {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1,1000, TimeUnit.MICROSECONDS,1,(queue,task)-> {
            //task.run();
            queue.offer(task, 1500, TimeUnit.MILLISECONDS);
        });
        for (int i =0;i<4;i++) {
            int j = i;
            threadPool.execute(()-> {
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
