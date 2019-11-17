package com.lgd.home.study.learn;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread {
    private static class UseRun implements Runnable{

        @Override
        public void run() {
            System.out.println("新线程runnable");

        }
    }
    private static class UseCall implements Callable {

        @Override
        public Object call() throws Exception {
            System.out.println("新线程Callable");
            return "CallResult";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseRun useRun=new UseRun();
        new Thread(useRun).start();
        Thread t=new Thread(useRun);
        t.start();
        t.interrupt();
        UseCall useCall=new UseCall();
        FutureTask<String> futureTask=new FutureTask<>(useCall);
        new Thread(futureTask).start();
        System.out.println("hah ");
        System.out.println(futureTask.get());
    }
}
