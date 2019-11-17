package com.lgd.home.study.learn4lock;

import com.lgd.home.study.learn.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseRWLock implements GoodService {
    private GoodsInfo goodsInfo;
    private final ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private final Lock getLock=readWriteLock.readLock();
    private final Lock setLock=readWriteLock.writeLock();

    public UseRWLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        getLock.lock();
        try {
            SleepTools.ms(5);
            return this.goodsInfo;
        }finally {
            getLock.unlock();
        }
    }

    @Override
    public void setNum(int number) {
        setLock.lock();
        try {
            SleepTools.ms(5);
            goodsInfo.changeNumber(number);
        }finally {
            setLock.unlock();
        }
    }
}
