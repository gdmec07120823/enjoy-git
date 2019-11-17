package com.lgd.home.study.learn4lock;

import com.lgd.home.study.learn.SleepTools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BusiApp {
    static final int READWRITERATIO=10;
    static final int MINTHREADCOUNT=3;
    static CountDownLatch countDownLatch=new CountDownLatch(1);
    static class GetThread implements Runnable{
        private GoodService goodService;

        public GetThread(GoodService goodService) {
            this.goodService = goodService;
        }

        @Override
        public void run() {
            /*try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            long start=System.currentTimeMillis();
            for (int i=0;i<100;i++){
                goodService.getNum();
            }
            System.out.println(Thread.currentThread().getName()+" 线程获取商品信息耗时："+(System.currentTimeMillis()-start)+" ms");
        }
    }
    static class SetThread implements Runnable{
        private GoodService goodService;

        public SetThread(GoodService goodService) {
            this.goodService = goodService;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start=System.currentTimeMillis();
            Random random=new Random();
            for (int i=0;i<10;i++){
                SleepTools.ms(50);
                goodService.setNum(random.nextInt(10));
            }
            System.err.println(Thread.currentThread().getName()+" 线程修改商品信息耗时："+(System.currentTimeMillis()-start)+" ms");
        }
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo=new GoodsInfo("CPU",10000,100000);
       // GoodService goodService=new UseSyn(goodsInfo);
        GoodService goodService=new UseRWLock(goodsInfo);
        for (int i=0;i<MINTHREADCOUNT;i++){
            Thread seT=new Thread(new SetThread(goodService));
            for (int j=0;j<READWRITERATIO;j++){
                Thread getT=new Thread(new GetThread(goodService));
                getT.start();
            }
            SleepTools.ms(10);
            seT.start();
        }
        SleepTools.ms(100);
       // countDownLatch.countDown();
    }
}
