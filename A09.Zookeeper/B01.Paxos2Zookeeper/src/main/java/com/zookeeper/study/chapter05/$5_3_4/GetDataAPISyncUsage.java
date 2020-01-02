package com.zookeeper.study.chapter05.$5_3_4;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class GetDataAPISyncUsage implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();


    @Override
    public void process(WatchedEvent event) {

        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                try {
                    byte[] data = zk.getData(event.getPath(), true, stat);
                    System.out.println(new String(data));

                    System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());
                } catch (Exception e) {
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("172.16.144.145:2181", 5000, new GetDataAPISyncUsage());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(new String(zk.getData(path, true, stat)));
        System.out.println(stat.getCzxid() + " ," + stat.getMzxid() + ", " + stat.getVersion());

        zk.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
