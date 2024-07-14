package com.example.threadTest.test001;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j(topic = "c.BlockingQueue")

public class BlockingQueue<T> {
	// 1. 任务队列
	private Deque<T> queue = new ArrayDeque<>();
	// 2. 锁
	private ReentrantLock lock = new ReentrantLock();
	// 3. 生产者条件变量
	private Condition fullWaitSet = lock.newCondition();
	// 4. 消费者条件变量
	private Condition emptyWaitSet = lock.newCondition();
	// 5. 容量
	private int capcity;
	public BlockingQueue(int capcity) {
		this.capcity = capcity;
	}
	// 带超时阻塞获取
	public T poll(long timeout, TimeUnit unit) {
		lock.lock();
		try {
			// 将 timeout 统一转换为 纳秒
			long nanos = unit.toNanos(timeout);
			while (queue.isEmpty()) {
				try {
					// 返回值是剩余时间
					if (nanos <= 0) {
						return null;
					}
					nanos = emptyWaitSet.awaitNanos(nanos);
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

	// 带超时时间阻塞添加
	public boolean offer(T task, long timeout, TimeUnit timeUnit) {
		lock.lock();
		try {
			long nanos = timeUnit.toNanos(timeout);
			while (queue.size() == capcity) {
				try {
					if(nanos <= 0) {
						return false;
					}

					System.out.println("等待加入任务队列  ..."+task);
					nanos = fullWaitSet.awaitNanos(nanos);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("加入任务队列"+task);
			queue.addLast(task);
			emptyWaitSet.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}
	public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
		lock.lock();
		try {
			// 判断队列是否满
			if(queue.size() == capcity) {
				rejectPolicy.reject(this, task);
			} else { // 有空闲
				System.out.println("加入任务队列 "+ task);
				queue.addLast(task);
				emptyWaitSet.signal();
			}
		} finally {
			lock.unlock();
		}
	}
}
