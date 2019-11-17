package com.lgd.home.study.learn.waitnotify;

public class Express {
    public final static String CITY="ShangHai";
    private int km;//快递运输里程数
    private String site;/*快递到达地点*/

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm(){
        this.km=101;
        notifyAll();
    }
    public synchronized void changeSite(){
        this.site="BeiJing";
        notifyAll();
    }

    public synchronized void waitKm(){
        while (this.km<=100){
            try {
                wait();
                System.out.println("check km thread["+Thread.currentThread().getId()+"] is be notifed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the km is "+this.km+",you should change db");
    }

    public synchronized void waitSite(){
        while (CITY.equals(this.site)){
            try {
                wait();
                System.out.println("check site thread["+Thread.currentThread().getId()+"] is be notifed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is "+this.km+",you should change db");
    }
}
