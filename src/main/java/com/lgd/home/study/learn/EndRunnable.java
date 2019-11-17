package com.lgd.home.study.learn;

public class EndRunnable {
    private static class UseRunnable implements Runnable{

        @Override
        public void run() {
            String threadName=Thread.currentThread().getName();
            int i=1;
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(threadName+" is run! number:"+i++);
            }
            System.out.println(threadName+" interrupt flag is "+Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRunnable useRunnable =new UseRunnable();
        Thread useThread=new Thread(useRunnable,"UseRunnable");
        useThread.start();
        Thread.sleep(20);
        useThread.interrupt();
    }
}
