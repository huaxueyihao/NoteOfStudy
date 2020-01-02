package com.zookeeper.study.chapter05.$5_3_1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperConstructorUsageSimple implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event: "+watchedEvent);
        // 接收服务端发来的SyncConnected事件，解除主程序在CountDownLatch上等待阻塞。
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            System.out.println("123");
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws IOException {

        ZooKeeper zooKeeper = new ZooKeeper("172.16.144.145:2181", 5000, new ZookeeperConstructorUsageSimple());

        System.out.println(zooKeeper.getState());

        try {
            connectedSemaphore.await();
        }catch (InterruptedException e){
            System.out.println("Zookeeper session established. ");
        }


    }
}
