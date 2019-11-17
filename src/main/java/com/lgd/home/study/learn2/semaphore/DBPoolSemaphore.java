package com.lgd.home.study.learn2.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 使用信号灯Semaphore实现数据库连接池
 */
public class DBPoolSemaphore {
    private final static int POOL_SIZE=10;
    private final Semaphore useful,useless;//可用连接，已用连接

    public DBPoolSemaphore() {
        this.useful=new Semaphore(POOL_SIZE);
        this.useless=new Semaphore(0);
    }

    private static LinkedList<Connection> pool=new LinkedList<>();
    static {
        for (int i=0;i<POOL_SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public void returnConnect(Connection conn) {
        if(conn!=null){
            try {
                System.out.println("当前有"+useful.getQueueLength()+"个线程等待了数据库连接!"+
                        "当前可用连接数有:"+useful.availablePermits());
                //useless.acquire();
                synchronized (pool){
                    pool.addLast(conn);
                }
                useful.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Connection takeConnect() throws InterruptedException {
        useful.acquire();
        Connection conn;
        synchronized (pool){
            conn=pool.removeFirst();
        }
       // useless.release();
        return conn;
    }

}
