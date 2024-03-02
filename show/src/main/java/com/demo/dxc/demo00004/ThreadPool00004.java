package com.demo.dxc.demo00004;

public interface ThreadPool00004<Job extends Runnable> {

    void execute(Job job); // 执行一个Job，这个Job需要实现Runnable

    void shutdown(); // 关闭线程池

    void addWorkers(int num); // 增加工作者线程

    void removeWorker(int num); // 减少工作者线程

    int getJobSize(); // 得到正在等待执行的任务数量
}
