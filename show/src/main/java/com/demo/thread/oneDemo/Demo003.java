package com.demo.thread.oneDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class Demo003 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask<Integer>  tesk =  new FutureTask<>(()-> {
//            log.info("tesk任务");
//            return 500;
//        });
//
//        new Thread(tesk,"nnv").start();
//
//        Integer integer = tesk.get();
//        log.info("FutureTask<Integer> 返回数据为{}",integer);

        FutureTask<Integer> tesk0001 =  new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("Callable");
                Thread.sleep(1000);
                return 100;
            }
        });
        new Thread(tesk0001,"tesk001").start();
        log.info("FutureTask 返回结果{}",tesk0001.get());

    }
}
