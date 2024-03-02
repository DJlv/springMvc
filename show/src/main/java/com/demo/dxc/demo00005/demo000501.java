package com.demo.dxc.demo00005;

import freemarker.core.JSONOutputFormat;

public class demo000501 {
    private static String A = "A";
    private static String B  = "B";
    public static void main(String[] args) {

    }

    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.printf("1");
                    }
                }
            }
        });
        Thread t2 =new Thread(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.printf("2");
                }
            }
        });
        t1.start();
        t2.start();
    }
}