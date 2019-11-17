package com.lgd.home.study.learn;

public class UseJoin {
    public static class JumpQueue implements Runnable{
        Thread jthread;

        public JumpQueue(Thread jthread) {
            this.jthread = jthread;
        }

        @Override
        public void run() {
            try {
                jthread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ " is terminted!");
        }
    }

    public static void main(String[] args) {
        Thread prethread=Thread.currentThread();
        for (int i=0;i<=10;i++){
            Thread thread=new Thread(new JumpQueue(prethread),"thread"+i);
            System.out.println(prethread.getName()+" jump a queue the thread:"+thread.getName());
            thread.start();
            prethread=thread;
        }
        SleepTools.ms(2000);
        System.out.println(Thread.currentThread().getName()+" is terminted!");
    }
}
