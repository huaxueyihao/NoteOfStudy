package com.zookeeper.study.chapter05.$5_3_4;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperGetChildrenAPIASyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;


    public static void main(String[] args) throws Exception {

        String path = "/zk-book";

        zk = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperGetChildrenAPIASyncUsage());

        connectedSemaphore.await();


        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zk.getChildren(path, true, new IChildren2Callback(), null);
        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);


    }


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

    static class IChildren2Callback implements AsyncCallback.Children2Callback {

        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path
                    + ", ctx: " + ctx + ", children list: " + children + ", stat: " + stat);
        }

    }
}
