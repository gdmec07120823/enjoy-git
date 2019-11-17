package com.lgd.home.study.learn2.threadtools;

import com.lgd.home.study.learn.SleepTools;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {
    static CountDownLatch latch=new CountDownLatch(6);
    private static class InitThread implements Runnable{
        @Override
        public void run() {
            System.out.println("The Thread is Init: "+Thread.currentThread().getName());
            latch.countDown();
            System.out.println("The CountDownLatch are countDown"+latch.getCount());
        }
    }
    private static class BussThread implements Runnable{
        @Override
        public void run() {
            System.out.println("The bussThread are begin await: "+Thread.currentThread().getName());
            try {
                latch.await();
                System.out.println("The BussThread Begin work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getName()+" Ready init work step 1st...");
                latch.countDown();
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getName()+" Ready init work step 1st...");
                latch.countDown();
                System.out.println("step over!");
            }
        }).start();
        new Thread(new BussThread()).start();
        for (int i=0;i<=3;i++){
            new Thread(new InitThread()).start();
        }
        try {

            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main work over!");
    }

}
