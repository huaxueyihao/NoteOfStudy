package com.zookeeper.study.chapter05.$5_3_4;

import com.zookeeper.study.chapter05.$5_3_3.DeleteAPISyncUsage;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperGetChildrenAPISyncUsage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("ReGet Child: " + zk.getChildren(event.getPath(), true));
                } catch (Exception e) {
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String path = "/zk-book";

        zk = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperGetChildrenAPISyncUsage());

        connectedSemaphore.await();


        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> children = zk.getChildren(path, true);
        System.out.println(children);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);


    }
}
