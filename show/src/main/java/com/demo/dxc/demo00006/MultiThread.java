package com.demo.dxc.demo00006;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@Slf4j
public class MultiThread {
    public static void main(String[] args) {
        mut();
    }
    public static void mut() {
        ThreadMXBean th = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = th.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            log.info("{},{}",threadInfo.getThreadId(),threadInfo.getThreadName());
        }
    }
}
