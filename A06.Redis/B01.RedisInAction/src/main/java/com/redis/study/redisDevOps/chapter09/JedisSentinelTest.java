package com.redis.study.redisDevOps.chapter09;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JedisSentinelTest {


    public static void main(String[] args) {


        jedisSentinelPoolInit();


    }

    private static void jedisSentinelPoolInit() {
        String masterName = "";
        Set<String> sentinelSet = null;
//        String poolConfig = "";
//        int timeout = 0;
//        new JedisSentinelPool(masterName,sentinelSet,poolConfig,timeout);

        initSentinels(sentinelSet,masterName);
    }

    private static void initSentinels(Set<String> sentinelSet, String masterName) {
        // 主节点
        HostAndPort master = null;
        // 遍历sentinel节点
        for (String sentinel : sentinelSet) {
            // 连接sentinel节点
            HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
            Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
            // 使用sentinel get-master-addr-by-name masterName 获取主节点信息
            List<String> masterAddr = jedis.sentinelGetMasterAddrByName(masterName);

            // 命令返回列表为空或者长度为2，继续从下一个sentinel节点查询
            if(masterAddr == null || masterAddr.size() != 2){
                continue;
            }
            // 解析masterAddr获取主节点信息
            master = toHostAndPort(masterAddr);
            break;
        }

        if(master == null){
            throw new RuntimeException();
        }

        // 为每个sentinel节点开启switch的监控线程
        for (String sentinel : sentinelSet) {
            HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));


        }




    }

    private static HostAndPort toHostAndPort(List<String> asList) {

        return null;
    }


}
