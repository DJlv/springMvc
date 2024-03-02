package com.demo.dxc.demo00004;


import com.demo.itcast.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo00004 {
    public static void main(String[] args) {
        DefaultThreadPool00004<Runnable> runnableDefaultThreadPool00004 = new DefaultThreadPool00004<>();
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            runnableDefaultThreadPool00004.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        log.info("Runable执行中{}", finalI);
                        Sleeper.sleep(10);
                    }
                }
            });
        }

    }
}
