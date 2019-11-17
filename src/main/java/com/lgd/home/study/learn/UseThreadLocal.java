package com.lgd.home.study.learn;

public class UseThreadLocal {
    private static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    public void runTestThreadLocal(){
        TestThread[] testThreads=new TestThread[4];
        for (int i=0;i<testThreads.length;i++){
            testThreads[i]=new TestThread(i);
        }
        for (TestThread testThread: testThreads){
            testThread.start();
        }
    }

    private static class TestThread extends Thread{
        int id;
        public TestThread(int id) {
            this.id=id;
        }
        @Override
        public void run() {
            String threadName=Thread.currentThread().getName();
            System.out.println(threadName+" value:strat");
            Integer val=threadLocal.get();
            val=val+id;
            threadLocal.set(val);
            System.out.println(threadName+" value:"+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal=new UseThreadLocal();
        useThreadLocal.runTestThreadLocal();
        System.out.println(threadLocal.get());
    }
}
