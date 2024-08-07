package com.demo.dxc.demo00004;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class DefaultThreadPool00004<Job extends Runnable> implements ThreadPool00004<Job> {

    private static final int MAX_WORKER_NUMBERS = 10; // 线程池最大限制数

    private static final int DEFAULT_WORKER_NUMBERS = 5; // 线程池默认的数量

    private static final int MIN_WORKER_NUMBERS = 1; // 线程池最小的数量

    private final LinkedList<Job> jobs = new LinkedList<Job>(); // 这是一个工作列表，将会向里面插入工作

    // 工作者列表
    private final List<Worker00004> workers = Collections.synchronizedList(new
            ArrayList<Worker00004>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool00004() {
        initializeWokers(DEFAULT_WORKER_NUMBERS);
    }


    // 初始化线程工作者
    private void initializeWokers(int num) {
        log.info("initializeWokers方法开始{}",num);
        for (int i = 0; i < num; i++) {
            log.info("第{}个Worker00004",i);
            Worker00004 worker = new Worker00004();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-" + threadNum.
                    incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            // 添加一个工作，然后进行通知
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker00004 worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            // 限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWokers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            // 按照给定的数量停止Worker
            int count = 0;
            while (count < num) {
                Worker00004 worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    // 工作者，负责消费任务
    class Worker00004 implements Runnable {
        // 是否工作
        private volatile boolean running = true;

        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    // 如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException ex) {
                            // 感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    // 取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                        log.info("worker00004开始工作");
                    } catch (Exception ex) {
                        // 忽略Job执行中的Exception
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
