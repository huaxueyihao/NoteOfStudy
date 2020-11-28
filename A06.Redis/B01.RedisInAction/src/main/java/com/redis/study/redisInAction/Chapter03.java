package com.redis.study.redisInAction;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Chapter03 {

    public static void main(String[] args) {


//        testString();
//        testList();

//        testSet();

//        testHash();

//        testZSet();

        testTimeout();

    }

    private static void testTimeout() {

        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);


        conn.set("key","value");
        System.out.println(conn.get("key"));

        Long key = conn.expire("key", 2);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(conn.get("key"));

        conn.set("key","value2");

        conn.expire("key",100);
        System.out.println(conn.ttl("key"));





    }

    private static void testZSet() {
        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);

        HashMap<String, Double> map = new HashMap<String, Double>();
        map.put("a", 3D);
        map.put("b", 2D);
        map.put("c", 1D);
        // 添加元素
        conn.zadd("zset-key", map);

        // 返回有序集合的成员数量
        Long zcard = conn.zcard("zset-key");
        System.out.println(zcard);

        // 将某个member的分值加上increment
        Double c = conn.zincrby("zset-key", 3, "c");
        System.out.println(c);

        // 返回成员变量的分值
        Double b = conn.zscore("zset-key", "b");
        System.out.println(b);
        // 返回排名
        Long c1 = conn.zrank("zset-key", "c");
        System.out.println(c1);

        // 返回成员变量分值min和max之间的数量
        Long zcount = conn.zcount("zset-key", 0, 3);
        System.out.println(zcount);

        // 移除给定的成员变量
        Long b1 = conn.zrem("zset-key", "b");
        System.out.println(b1);


        // 返回有序集合中排名在start和end之间的成员
        Set<String> zrange = conn.zrange("zset-key", 0, -1);
        System.out.println(Arrays.toString(zrange.toArray()));

    }

    private static void testHash() {
        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);

//        HashMap<String, String> hashMap = new HashMap<String, String>();
//        hashMap.put("k1", "v1");
//        hashMap.put("k2", "v2");
//        hashMap.put("k3", "v3");
//        hashMap.put("k4", "v4");
//
//        // 设置值
//        String hmset = conn.hmset("hash-key", hashMap);
//
//        // 从散列中获取一个或多个key的值
//        List<String> hmget = conn.hmget("hash-key", "k1", "k2");
//
//        System.out.println(Arrays.toString(hmget.toArray()));
//
//        // 返回散列包含的键值对数量
//        System.out.println(conn.hlen("hash-key"));
//
//        // 移除散列中的键值对
//        conn.hdel("hash-key", "k1", "k2");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("short", "hello");
        map.put("long", "1000*1");

        // 设置值
        String hmset = conn.hmset("hash-key2", map);

        // 获得散列的所有key
        Set<String> hkeys = conn.hkeys("hash-key2");
        System.out.println(Arrays.toString(hkeys.toArray()));

        // 判断散列中是否有某个key
        System.out.println(conn.hexists("hash-key2", "num"));

        // 将散列上num的值加上3
        Long num = conn.hincrBy("hash-key2", "num", 3);
        System.out.println(num);


    }

    private static void testSet() {
        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);

        // 将多个元素添加到集合中
//        Long sadd = conn.sadd("set-key", "a", "b", "c");
//
//        // 删除元素，返回1，代表有元素被删除，返回0，代表没有删除任何元素
//        System.out.println(conn.srem("set-key","c","d"));
//        System.out.println(conn.srem("set-key","c","d"));

//        // 获取集合中所有元素
//        Set<String> smembers = conn.smembers("set-key");
//        System.out.println(Arrays.toString(smembers.toArray()));
//
//        // 从集合set-key中移除元素a，并添加到set-key2集合中
//        conn.smove("set-key", "set-key2", "a");
//
//
//        System.out.println(Arrays.toString(conn.smembers("set-key").toArray()));
//        System.out.println(Arrays.toString(conn.smembers("set-key2").toArray()));


        conn.sadd("skey1", "a", "b", "c", "d");
        conn.sadd("skey2", "c", "d", "e", "f");

        // 返回存在于第一个集合，不存在与第二个集合的元素：差集
        Set<String> sdiff = conn.sdiff("skey1", "skey2");
        System.out.println(Arrays.toString(sdiff.toArray()));

        // 返回两个集合同时存在的元素：交集
        Set<String> sinter = conn.sinter("skey1", "skey2");
        System.out.println(Arrays.toString(sinter.toArray()));

        // 并集运算
        Set<String> sunion = conn.sunion("skey1", "skey2");
        System.out.println(Arrays.toString(sunion.toArray()));

    }

    private static void testList() {
        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);

        // 将一个值推入列表的右端
//        Long last = conn.rpush("list-key", "last");
        // 将一个值推入列表的左端
//        Long lpush = conn.lpush("list-key", "new last");

//        移除并返回最左端的元素
//        String lpop = conn.lpop("list-key");
//        System.out.println("lpop=" + lpop);

        // 对列表进行修剪，保留从start到end之间的元素
//        String ltrim = conn.ltrim("list-key", 1, -1);

//        System.out.println("ltrim=" + ltrim);

        // 返回列表从start偏移量到end偏移量范围内的所有元素，其中偏移量为start和偏移量为end的元素也会包含在被返回的元素之内
//        List<String> list = conn.lrange("list-key", 0, -1);


        conn.rpush("list", "item1");
        conn.rpush("list", "item2");
        conn.rpush("list2", "item3");
        // 阻塞地将一个列表中的元素移到另一个列表中
        conn.brpoplpush("list2", "list", 1);

        System.out.println(Arrays.toString(conn.lrange("list", 0, -1).toArray()));


    }

    private static void testString() {
        Jedis conn = new Jedis("172.16.144.145", 6379);
        conn.select(15);

        String key = conn.get("key");
        System.out.printf("key=%s\n", key);

//        自增加1,返回加1后的结果
        Long incr = conn.incr("key");
        System.out.printf("自增加%d\n", incr);

        Long decr = conn.decr("key");
        System.out.printf("自动减%d\n", decr);

//        对字符串进行追加
        Long hello = conn.append("new-string-key", "hello");
        System.out.printf("append=%d\n", hello);

        System.out.printf("new-string-key=%s\n", conn.get("new-string-key"));

        // 截取字符串
        String substr = conn.substr("new-string-key", 3, 7);
        System.out.printf("substr=%s\n", substr);

        // 设置指定位置的值
        Long setrange = conn.setrange("new-string-key", 0, "H");
        System.out.println("setrange=" + setrange);
        System.out.printf("new-string-key=%s\n", conn.get("new-string-key"));

    }


}
