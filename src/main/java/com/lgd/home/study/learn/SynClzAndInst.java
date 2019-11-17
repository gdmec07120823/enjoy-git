package com.lgd.home.study.learn;

public class SynClzAndInst {

    private static class SynClass extends Thread{
        @Override
        public void run() {
            System.out.println("TestClass is Running!...");
            try {
                synClass();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InstanceSyn implements Runnable{
        SynClzAndInst synClzAndInst;
        public InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst=synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("InstanceSyn is Running!..."+synClzAndInst);
            try {
                synClzAndInst.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Instance2Syn implements Runnable{
        SynClzAndInst synClzAndInst;
        public Instance2Syn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst=synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("Instance2Syn is Running!..."+synClzAndInst);
            try {
                synClzAndInst.instance2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private  synchronized void instance() throws InterruptedException {
        synchronized (this.getClass()){
            Thread.sleep(3000);
            System.out.println("SynInstance is going..."+this.toString());
            Thread.sleep(3000);
            System.out.println("SynInstance is ended..."+this.toString());
        }

    }

    private  synchronized void instance2() throws InterruptedException {
        synchronized (this.getClass()) {
            Thread.sleep(3000);
            System.out.println("SynInstance2 is going..." + this.toString());
            Thread.sleep(3000);
            System.out.println("SynInstance2 is ended..." + this.toString());
        }
    }

    private static synchronized  void synClass() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("SynClass is going...");
        Thread.sleep(1000);
        System.out.println("SynClass is ended...");
    }

    public static void main(String[] args) {

        SynClzAndInst syn1=new SynClzAndInst();
        Thread ins1=new Thread(new InstanceSyn(syn1));

        SynClzAndInst syn2=new SynClzAndInst();
        Thread ins2=new Thread(new Instance2Syn(syn2));

        ins1.start();
        ins2.start();
//        SynClass synClass=new SynClass();
//        synClass.start();
    }
}
