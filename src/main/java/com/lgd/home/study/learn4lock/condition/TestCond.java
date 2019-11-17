package com.lgd.home.study.learn4lock.condition;

import com.lgd.home.study.learn.waitnotify.Express;

public class TestCond {
    private static ExpressCond express=new ExpressCond(0, Express.CITY);

    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<3;i++){
            new CheckSite().start();
        }
        for (int i=0;i<3;i++){
            new CheckKm().start();
        }
        Thread.sleep(1000);
        express.changeKm();
    }
}
