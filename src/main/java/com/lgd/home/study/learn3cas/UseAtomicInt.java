package com.lgd.home.study.learn3cas;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {
    private static AtomicInteger atomicInteger=new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.get());
    }
}
