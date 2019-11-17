package com.lgd.home.study.learn2.forkjoin.sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumArray {
    private static class SumTask extends RecursiveTask<Integer>{
        private final static int THEREHOLD=MakeArray.ARRAY_LENGTH/10;
        private int[] src;//要统计的数据
        private int fromindex;//开始下标
        private int toindex;//结束下标

        public SumTask(int[] src, int fromindex, int toindex) {
            this.src = src;
            this.fromindex = fromindex;
            this.toindex = toindex;
        }

        @Override
        protected Integer compute() {
            if (toindex-fromindex<THEREHOLD){
                int count=0;
                for(int i=fromindex;i<=toindex;i++){
                    //SleepTools.ms(1);
                    count=count+src[i];
                }
                return count;
            }else {
                int mid=(fromindex+toindex)/2;
                SumTask left=new SumTask(src,fromindex,mid);
                SumTask right=new SumTask(src,mid+1,toindex);
                invokeAll(left,right);
                return left.join()+right.join();
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();
        int[] src=MakeArray.makeArray();
        SumTask innerFind=new SumTask(src,0,src.length-1);
        long start=System.currentTimeMillis();
        pool.invoke(innerFind);
        System.out.println("task is running...");
        System.out.println("计算出来的数值为："+innerFind.join()+" 总共用时："+(System.currentTimeMillis()-start)+" ms");
    }
}
 