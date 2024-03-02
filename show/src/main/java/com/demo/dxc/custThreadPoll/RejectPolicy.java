package com.demo.dxc.custThreadPoll;

// 拒绝策略
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> deque, T task);
}
