package com.example.threadTest.test002;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue_V_Test<T> {

	private Deque<T> tDeque = new ArrayDeque<>();

	private ReentrantLock lock = new ReentrantLock();

	private Condition fullWaitSet = lock.newCondition();

	private Condition emptyWaitSet = lock.newCondition();

	private int capcity;

	public BlockingQueue_V_Test(int capcity) {
		this.capcity = capcity;
	}

	// 带超时阻塞获取

	public T poll(long timeOut, TimeUnit unit) {
		lock.lock();
		try {
			long nanos = unit.toNanos(timeOut);
			while (tDeque.isEmpty()) {
				try {
					if (nanos <= 0) {
						return null;
					}
					nanos = emptyWaitSet.awaitNanos(nanos);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			T t = tDeque.removeFirst();
			fullWaitSet.signal();
			return t;
		} finally {
			lock.unlock();
		}
	}

	public boolean offer(T task, long timeOut, TimeUnit timeUnit) {
		lock.lock();
		try {
			long nanos = timeUnit.toNanos(timeOut);
			while (tDeque.size() == capcity) {
				try {
					if (nanos < 0) {
						return false;
					}
					System.out.println("任务队列已满，等待任务队列存在空出位置" + task);
					nanos = fullWaitSet.awaitNanos(nanos);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			System.out.println("加入任务队列" + task);
			tDeque.addLast(task);
			emptyWaitSet.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}

	public void tryPut(Reject_V_Test<T> reject_v_test, T task) {
		try {
			lock.lock();
			if (tDeque.size() == capcity) {
				reject_v_test.reject(this, task);
			} else {
				System.out.println("加入任务队列" + task);
				tDeque.addLast(task);
				emptyWaitSet.signal();
			}
		} finally {
			lock.unlock();
		}
	}

}
