package com.lgd.home.study.learn;

public class SleepTools {
    public static void ms(long s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
