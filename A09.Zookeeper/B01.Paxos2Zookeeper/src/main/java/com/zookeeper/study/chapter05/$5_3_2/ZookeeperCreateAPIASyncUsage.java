package com.zookeeper.study.chapter05.$5_3_2;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class ZookeeperCreateAPIASyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {


        ZooKeeper zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperCreateAPIASyncUsage());

        connectedSemaphore.await();


        // 临时节点
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(),"I am context.");
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(),"I am context.");
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(),"I am context.");

        Thread.sleep(Integer.MAX_VALUE);
    }


    static class IStringCallback implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("Create path result: [" + rc + ", " + path + ", "
                    + ctx + ", real path name: " + name);
        }
    }
}
