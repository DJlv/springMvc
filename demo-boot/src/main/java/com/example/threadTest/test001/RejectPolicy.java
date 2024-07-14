package com.example.threadTest.test001;




@FunctionalInterface // 拒绝策略
interface RejectPolicy<T>{
	void reject(BlockingQueue<T> queue, T task);
}
