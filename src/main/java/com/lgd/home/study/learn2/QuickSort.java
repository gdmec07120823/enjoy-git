package com.lgd.home.study.learn2;

import com.lgd.home.study.learn.SleepTools;
import com.lgd.home.study.learn2.forkjoin.sum.MakeArray;

import java.util.concurrent.RecursiveAction;

public class QuickSort {
    public static void main(String[] args) {
        //int[] array={30,69,3,18,17,29,2,10,23,0,5,232,223};
        int[] array= MakeArray.makeArray();
        long start=System.currentTimeMillis();
        sort(0,array.length-1,array);
        System.out.println("sort over time use:"+(System.currentTimeMillis()-start));

       /*start=System.currentTimeMillis();
        ForkJoinPool pool=new ForkJoinPool();
        SortTask task=new SortTask(0,array.length-1,array);
        pool.execute(task);
        System.out.println("begin task use time:"+(System.currentTimeMillis()-start));
        task.join();
        System.out.println("task execute use time:"+(System.currentTimeMillis()-start));*/
    }
    public static void sort(int start,int end,int[] array){
        if (start<end){
            int L=start,R=end;
            int temp=array[L];
            while (L!=R){
               // System.out.println(L+","+R);
                while (L<R){
                    if (array[R]<temp){//当右边的值小于左边的值时，进行替换
                        array[L]=array[R];
                        break;
                    }
                    R--;
                }
                while (R>L){
                    if (array[L]>temp){//当左边的值小于右边的值时，继续替换
                        array[R]=array[L];
                        break;
                    }
                    L++;
                }
                /*System.out.print("[");
                for (int i=0;i<array.length;i++){
                    System.out.print(" "+array[i]+",");
                }
                System.out.println("");*/
                SleepTools.ms(1);
            }
            array[L]=temp;
            /*System.out.print("[");
            for (int i=0;i<array.length;i++){
                System.err.print(" "+array[i]+",");
            }
            System.out.println("");*/
            sort(start,L-1,array);
            sort(L+1,end,array);
        }

    }
    private static class SortTask extends RecursiveAction{
        private int[] array;
        private int start;
        private int end;

        public SortTask( int start, int end,int[] array) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end>start){
                int L=start,R=end;
                int temp=array[L];
                while (L!=R){
                   // System.out.println(L+","+R);
                    while (L<R){
                        if (array[R]<temp){//当右边的值小于左边的值时，进行替换
                            array[L]=array[R];
                            break;
                        }
                        R--;
                    }
                    while (R>L){
                        if (array[L]>temp){//当左边的值小于右边的值时，继续替换
                            array[R]=array[L];
                            break;
                        }
                        L++;
                    }
                    /*System.out.print("[");
                    for (int i=0;i<array.length;i++){
                        System.out.print(" "+array[i]+",");
                    }
                    System.out.println("");*/
                    SleepTools.ms(1);
                }
                array[L]=temp;
                /*System.out.print("[");
                for (int i=0;i<array.length;i++){
                    System.err.print(" "+array[i]+",");
                }
                System.out.println("");*/
                SortTask st1=new SortTask(start,L-1,array);
                SortTask st2=new SortTask(L+1,end,array);
                invokeAll(st1,st2);
                st1.join();
                st2.join();
            }
        }
    }
}
