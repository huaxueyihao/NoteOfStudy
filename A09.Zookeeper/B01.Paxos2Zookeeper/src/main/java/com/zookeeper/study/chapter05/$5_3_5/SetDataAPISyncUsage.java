package com.zookeeper.study.chapter05.$5_3_5;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class SetDataAPISyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }


    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zk = new ZooKeeper("172.16.144.145:2181",
                5000, //
                new SetDataAPISyncUsage());
        connectedSemaphore.await();

        zk.create( path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL );
        zk.getData( path, true, null );

        // -1 表示以最新版本进行更新操作
        Stat stat = zk.setData( path, "456".getBytes(), -1 );
        System.out.println(stat.getCzxid()+","+
                stat.getMzxid()+","+
                stat.getVersion());
        Stat stat2 = zk.setData( path, "456".getBytes(), stat.getVersion() );
        System.out.println(stat2.getCzxid()+","+
                stat2.getMzxid()+","+
                stat2.getVersion());
        try {
            zk.setData( path, "456".getBytes(), stat.getVersion() );
        } catch ( KeeperException e ) {
            System.out.println("Error: " + e.code() + "," + e.getMessage());
        }
        Thread.sleep( Integer.MAX_VALUE );
    }






}
