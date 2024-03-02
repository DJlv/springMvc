package com.demo.dxc.Demo00002;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class TestThreadPoolExecutors {

    private List<String> strName = Arrays.asList("zxc","asd","qwe");
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 提交任务并保存Future对象
        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is running.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.info("线程被打断");
                }
                System.out.println("Task is completed.");
            }
        });
        executor.execute(()-> {
            log.info("2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 等待一段时间后取消任务
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean cancel = future.cancel(true);
        log.info("----------{}",cancel);

    }
}
