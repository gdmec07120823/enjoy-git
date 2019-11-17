package com.lgd.home.study.learn4lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpressCond {
    public final static String CITY="ShangHai";
    private int km;//快递运输里程数
    private String site;/*快递到达地点*/
    static Lock kmLock=new ReentrantLock();
    static Lock siteLock=new ReentrantLock();
    static Condition kmCond=kmLock.newCondition();
    static Condition siteCond=siteLock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public void changeKm(){
        kmLock.lock();
        try {
            this.km=101;
            kmCond.signal();
        }finally {
            kmLock.unlock();
        }
    }
    public void changeSite(){
        siteLock.lock();
        try {
            this.site="BeiJing";
            siteCond.signal();
        }finally {
            siteLock.unlock();
        }
    }

    public void waitKm(){
        kmLock.lock();
        try {
            while (this.km<=100){
                try {
                    kmCond.await();
                    System.out.println("check km thread["+Thread.currentThread().getId()+"] is be notifed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("the km is "+this.km+",you should change db");
        }finally {
            kmLock.unlock();
        }

    }

    public void waitSite(){
        siteLock.lock();
        try {
            while (CITY.equals(this.site)){
                try {
                    siteCond.await();
                    System.out.println("check site thread["+Thread.currentThread().getId()+"] is be notifed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("the site is "+this.km+",you should change db");
        }finally {
            siteLock.unlock();
        }

    }
}
