package com.zookeeper.study.chapter05.$5_3_5;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class SetDataAPIASyncUsage implements Watcher {

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
                new SetDataAPIASyncUsage());
        connectedSemaphore.await();

        zk.create( path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL );
        zk.setData( path, "345".getBytes(), -1, new IStatCallback(),null);

        Thread.sleep( Integer.MAX_VALUE );
    }


    private static class IStatCallback implements AsyncCallback.StatCallback {
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            if (rc == 0) {
                System.out.println("SUCCESS");
            }
        }
    }
}
