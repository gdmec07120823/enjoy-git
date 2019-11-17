package com.lgd.home.study.learn2.threadtools;

import com.lgd.home.study.learn.SleepTools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {
    private static CyclicBarrier barrier=new CyclicBarrier(5,new CollectThread());
    private static ConcurrentHashMap<String,Long> map=new ConcurrentHashMap<String, Long>();

    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            new Thread(new SubThread()).start();
        }
    }

    private static class CollectThread implements Runnable{
        @Override
        public void run() {
            StringBuilder result=new StringBuilder();
            for (Map.Entry<String,Long> workResult:map.entrySet()){
                result.append("["+workResult.getKey()+":"+workResult.getValue()+"]");
            }
            System.out.println("The Run Thread is: "+result);
            System.out.println("This is a barrier thread");
        }
    }
    private static class SubThread implements Runnable{

        @Override
        public void run() {
            map.put(Thread.currentThread().getName(),Thread.currentThread().getId());
            Random random=new Random();
            try {
                if (random.nextBoolean()){
                    SleepTools.ms(1000);
                    System.out.println("Im worked 1s!");
                }
                System.out.println("Im worked hard!");
                SleepTools.ms(1000+Thread.currentThread().getId());
                barrier.await();
                System.out.println("My bro all break up ,do work!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }
}
