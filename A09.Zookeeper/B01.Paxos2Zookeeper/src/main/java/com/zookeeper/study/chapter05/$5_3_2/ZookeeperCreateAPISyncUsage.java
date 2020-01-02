package com.zookeeper.study.chapter05.$5_3_2;

import com.zookeeper.study.chapter05.$5_3_1.ZookeeperConstructorUsageWithSIDPASSWD;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperCreateAPISyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws Exception {


        ZooKeeper zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperCreateAPISyncUsage());

        connectedSemaphore.await();


        // 临时节点
        String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        // 临时顺序节点
        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);

    }
}
