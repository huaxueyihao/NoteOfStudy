package com.zookeeper.study.chapter05.$5_3_3;

import com.zookeeper.study.chapter05.$5_3_2.ZookeeperCreateAPISyncUsage;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DeleteAPISyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {

        String path = "/zk-book";

        zk = new ZooKeeper("172.16.144.145:2181", 5000, new DeleteAPISyncUsage());

        connectedSemaphore.await();


        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);


    }


}
