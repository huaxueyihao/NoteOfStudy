package com.zookeeper.study.chapter05.$5_3_1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZookeeperConstructorUsageWithSIDPASSWD implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: "+event);
        // 接收服务端发来的SyncConnected事件，解除主程序在CountDownLatch上等待阻塞。
        if(Event.KeeperState.SyncConnected == event.getState()){
            System.out.println("123");
            connectedSemaphore.countDown();
        }
    }


    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperConstructorUsageWithSIDPASSWD());

        connectedSemaphore.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();

        zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperConstructorUsageWithSIDPASSWD(), 1l, "test".getBytes());

        zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperConstructorUsageWithSIDPASSWD(), sessionId, sessionPasswd);

        Thread.sleep(Integer.MAX_VALUE);


    }
}
