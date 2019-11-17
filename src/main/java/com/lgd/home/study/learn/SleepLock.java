package com.lgd.home.study.learn;

/**
 * 测试sleep是否会释放锁，结果是不会
 */
public class SleepLock {
    private Object lock=new Object();

    public static void main(String[] args) {
        SleepLock sl=new SleepLock();
        Thread t1=new Thread(sl.new ThreadSleep(),"lockthread");
        Thread t2=new Thread(sl.new ThreadNoSleep(),"Nolockthread");
        t1.start();
        SleepTools.ms(1000);
        t2.start();
    }

    private class ThreadSleep implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                System.out.println("i am got lock:"+Thread.currentThread().getName());
                SleepTools.ms(5000);
                System.out.println("i am finish lock:" +Thread.currentThread().getName());
            }
        }
    }

    private class ThreadNoSleep implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                System.out.println("i am got lock:"+Thread.currentThread().getName());
                SleepTools.ms(500);
                System.out.println("i am finish lock:" +Thread.currentThread().getName());
            }
        }
    }
}
