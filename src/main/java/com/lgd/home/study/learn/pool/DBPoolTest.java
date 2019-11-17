package com.lgd.home.study.learn.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
    static DBPool pool=new DBPool(10);

    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        int threadCount=50;
        end=new CountDownLatch(threadCount);
        int count=20;
        AtomicInteger got=new AtomicInteger();
        AtomicInteger noGot=new AtomicInteger();
        for (int i=0;i<threadCount;i++){
            Thread thread=new Thread(new Worker(count,got,noGot),"Worker_"+i);
            thread.start();
        }
        end.await();
        System.out.println("总共尝试了："+(threadCount*count));
        System.out.println("拿到连接次数："+got);
        System.out.println("没有拿到连接次数："+noGot);
    }

    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count>0){
                try {
                    Connection connection=pool.fetchConn(1000);
                    if(connection!=null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+" 等待超时!");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
