package com.demo.dxc.demo00007;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Priority {
    private static  volatile boolean netStart = true;
    private static  volatile boolean netEnd = true;
    public static void main(String[] args) {

        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY:Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            //thread.setDaemon(true);
            thread.start();
        }
        netStart = false;
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        netEnd = false;
        for (Job job : jobs) {
            log.info("{},{}",job.priority,job.jobCount);
        }
    }

    static class Job implements  Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while(netStart) {
                Thread.yield();
            }
            while (netEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
