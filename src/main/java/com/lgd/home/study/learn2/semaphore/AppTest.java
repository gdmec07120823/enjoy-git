package com.lgd.home.study.learn2.semaphore;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AppTest {
    private static DBPoolSemaphore dbPool=new DBPoolSemaphore();
    private static CyclicBarrier barrier=new CyclicBarrier(50);
    private static class BusThread extends Thread{
        @Override
        public void run() {
            Random r=new Random();
            Connection conn = null;
            try {
                barrier.await();
                long start=System.currentTimeMillis();
                conn=dbPool.takeConnect();
                System.out.println("获取数据库连接总用时:"+(System.currentTimeMillis()-start)+" ms");
                sleep(100+r.nextInt(100));
                System.out.println("业务操作完毕，总用时:"+(System.currentTimeMillis()-start)+"ms 准备归还连接");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }finally {
                dbPool.returnConnect(conn);
            }
        }
    }

    public static void main(String[] args) {
        for (int i=0;i<50;i++){
            new BusThread().start();
        }
    }

}
