package com.lgd.home.study.learn2.forkjoin.sum;

import com.lgd.home.study.learn.SleepTools;

public class SumNormal {
    public static void main(String[] args) {
        int count=0;
        int[] sumarray=MakeArray.makeArray();
        long start=System.currentTimeMillis();
        for (int num:sumarray){
            //SleepTools.ms(1);
            count=count+num;
        }
        System.out.println("计算出来的数值为："+count+" 总共用时："+(System.currentTimeMillis()-start)+" ms");
    }
}
