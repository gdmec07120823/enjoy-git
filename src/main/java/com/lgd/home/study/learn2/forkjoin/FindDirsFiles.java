package com.lgd.home.study.learn2.forkjoin;

import com.lgd.home.study.learn.SleepTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindDirsFiles extends RecursiveAction {
    private File path;//当前任务需要搜寻的目录

    public FindDirsFiles(File path) {
        this.path = path;
    }

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();
        FindDirsFiles task=new FindDirsFiles(new File("F:\\教程"));

        pool.execute(task);
        System.out.println("Task is running!");
        SleepTools.ms(1);
        int count=0;
        for (int i=0;i<100;i++){
            count=count+i;
        }
        System.out.println("Test Num is: "+ count);
        task.join();
        System.out.println("MainThreadEnd!!!!");
    }
    @Override
    protected void compute() {

        List<FindDirsFiles> subTasks=new ArrayList<>();

        File[] files=path.listFiles();
        if (files!=null){
            for (File file:files){
                if (file.isDirectory()){
                    subTasks.add(new FindDirsFiles(file));
                }else {
                    if (file.getAbsolutePath().endsWith("mp4")){
                        System.out.println("文件"+file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()){
                for (FindDirsFiles subTask:invokeAll(subTasks)){
                    subTask.join();//等待子任务执行完成
                }
            }
        }
    }
}
