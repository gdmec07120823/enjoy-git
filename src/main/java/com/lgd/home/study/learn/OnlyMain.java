package com.lgd.home.study.learn;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机线程管理接口
        ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos=
        threadMXBean.dumpAllThreads(false,false);

        for (ThreadInfo threadInfo: threadInfos){
            System.out.println("["+threadInfo.getThreadId()+"]"+" "+threadInfo.getThreadName());
        }
    }
}
