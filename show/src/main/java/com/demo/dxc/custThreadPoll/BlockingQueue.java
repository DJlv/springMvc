package com.demo.dxc.custThreadPoll;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class BlockingQueue<T> {

    private Deque<T> queue = new ArrayDeque<>(); // 1. 任务队列

    private ReentrantLock lock = new ReentrantLock(); // 2. 锁

    private Condition fullWaitSet = lock.newCondition(); // 3. 生产者条件变量

    private Condition emptyWaitSet = lock.newCondition(); // 4. 消费者条件变量

    private int capcity; // 5. 容量

    public BlockingQueue(int capcity) {this.capcity = capcity;}

    /**
     * 带超时阻塞获取
     * @param timeOut
     * @param unit
     * @return
     */
    public T poll(long timeOut, TimeUnit unit) {
        lock.lock();

        try {
            long nacos= unit.toNanos(timeOut);

            while(queue.isEmpty()) {

                try {
                    if(nacos < 0) {
                        return null;
                    }
                    emptyWaitSet.awaitNanos(nacos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 带超时时间的阻塞添加
    public boolean offer(T task, long timeOut, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nacos = timeUnit.toNanos(timeOut);
            while(queue.size() == capcity) {
                if(nacos < 0) {
                    return false;
                }
                try {
                    log.info("等待加入任务队列{}。。。。",task);
                    nacos = fullWaitSet.awaitNanos(nacos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("加入任务队列{}。。。。",task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();

        try {
            if(queue.size() == capcity) {
                rejectPolicy.reject(this, task);
            } else {
                log.info("加入任务队列{},",task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}
