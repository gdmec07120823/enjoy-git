package com.lgd.home.study.learn;

public class SynTest {
    private volatile  int age=100000;

    private static class VolatileVar implements Runnable{
        private volatile int a=0;
        @Override
        public void run() {
            String threadName=Thread.currentThread().getName();
            try {
                a=a+1;
                System.out.println(threadName+":a========== "+a);
                Thread.sleep(100);
                a=a+1;
                System.out.println(threadName+":a========== "+a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        VolatileVar volatileVar=new VolatileVar();
        Thread thread1=new Thread(volatileVar);
        Thread thread2=new Thread(volatileVar);
        Thread thread3=new Thread(volatileVar);
        Thread thread4=new Thread(volatileVar);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}
