package com.example.threadTest.test001;


import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPool {

	// 任务队列
	private BlockingQueue<Runnable> taskQueue;
	// 线程集合
	private HashSet<Worker> workers = new HashSet<>();
	// 核心线程数
	private int coreSize;
	// 获取任务时的超时时间
	private long timeout;
	private TimeUnit timeUnit;
	private RejectPolicy<Runnable> rejectPolicy;
	// 执行任务
	public void execute(Runnable task) {
		// 当任务数没有超过 coreSize 时，直接交给 worker 对象执行
		// 如果任务数超过 coreSize 时，加入任务队列暂存
		synchronized (workers) {
			if(workers.size() < coreSize) {
				Worker worker = new Worker(task);
				System.out.println("新增 worker:::"+worker+"========" +"task::::" + task);
				workers.add(worker);
				worker.start();
			} else {
				// 1) 死等
				// 2) 带超时等待
				// 3) 让调用者放弃任务执行
				// 4) 让调用者抛出异常
				// 5) 让调用者自己执行任务
				taskQueue.tryPut(rejectPolicy, task);
			}
		}
	}
	public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapcity,
					  RejectPolicy<Runnable> rejectPolicy) {
		this.coreSize = coreSize;
		this.timeout = timeout;
		this.timeUnit = timeUnit;
		this.taskQueue = new BlockingQueue<>(queueCapcity);
		this.rejectPolicy = rejectPolicy;
	}
	class Worker extends Thread{
		private Runnable task;
		public Worker(Runnable task) {
			this.task = task;
		}
		@Override
		public void run() {
			// 执行任务
			// 1) 当 task 不为空，执行任务
			// 2) 当 task 执行完毕，再接着从任务队列获取任务并执行
			while(task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
				try {
					System.out.println("正在执行...---------"+ task);
					task.run();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					task = null;
				}
			}
			synchronized (workers) {
				System.out.println("worker 被移除---------"+this);
				workers.remove(this);
			}
		}
	}
}