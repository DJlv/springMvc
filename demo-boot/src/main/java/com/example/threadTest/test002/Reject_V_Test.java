package com.example.threadTest.test002;



@FunctionalInterface
interface Reject_V_Test<T> {
	void reject(BlockingQueue_V_Test<T> queue,T task);
}
