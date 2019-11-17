package com.lgd.home.study.learn2.future;

import com.lgd.home.study.learn.SleepTools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UseFuture {
    private static class CalcuThread implements Callable<Integer>{
        private  int sum;
        @Override
        public Integer call() throws Exception {

                for (int i=0;i<=500&&!Thread.currentThread().isInterrupted();i++){
                    Thread.sleep(1);
                    sum=i+sum;
                }
                System.out.println("计算结束计算结果是："+sum);
            return sum;
        }
    }

    public static void main(String[] args) {
        FutureTask<Integer> futureTask=new FutureTask<Integer>(new CalcuThread());
        new Thread(futureTask).start();
        SleepTools.ms(10);
       // futureTask.cancel(true);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
