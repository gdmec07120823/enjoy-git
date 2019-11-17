package com.lgd.home.study.learn;

public class HasInterruptedException {
    private static class UseThread extends Thread{

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName=Thread.currentThread().getName();
            while (!isInterrupted()){
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.out.println(threadName+"interrupt flag is "+isInterrupted());
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(threadName);
            }
            System.out.println(threadName+"interrupt flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread=new UseThread("endThread");
        endThread.start();
        Thread.sleep(520);
        endThread.interrupt();
    }
}
