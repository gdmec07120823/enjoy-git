package com.lgd.home.study.learn3cas;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr=new AtomicStampedReference<>("LGD",0);

    public static void main(String[] args) throws InterruptedException {
        String oldReference=asr.getReference();
        int oldstamp=asr.getStamp();
        System.out.println(oldReference+"++++++++++++++"+oldstamp);
        Thread rightThread=new Thread(() -> {
            System.out.println("当前线程"+Thread.currentThread().getName()+"获取到的值为："+asr.getReference()+"版本戳为："+asr.getStamp()+"更改结果为："+
                    asr.compareAndSet(oldReference,oldReference+"java",oldstamp,oldstamp+1));
        });
        Thread errorThread=new Thread(() -> {
            System.out.println("当前线程"+Thread.currentThread().getName()+"获取到的值为："+asr.getReference()+"版本戳为："+asr.getStamp()+"更改结果为："+
                    asr.compareAndSet(oldReference,oldReference+"java",oldstamp,oldstamp+1));
        });
        rightThread.start();
        rightThread.join();
        errorThread.start();
        errorThread.join();
        System.out.println(asr.getReference()+"++++++++++++++++++"+asr.getStamp());
    }
}
