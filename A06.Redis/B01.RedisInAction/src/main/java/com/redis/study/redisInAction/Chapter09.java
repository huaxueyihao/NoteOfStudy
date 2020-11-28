package com.redis.study.redisInAction;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class Chapter09 {


    public static void main(String[] args) {

        new Chapter09().run();

    }

    public void run() {
        Jedis conn = new Jedis("172.16.144.161", 6379);
        conn.select(15);
        conn.flushDB();


        testLongZiplistPerformance(conn);
    }

    public void testLongZiplistPerformance(Jedis conn) {
        System.out.println("\n---- testLongZiplistPerformance -----");

        longZiplistPerformance(conn, "test", 5, 10, 10);

        assert conn.llen("test") == 5;
    }

    private double longZiplistPerformance(Jedis conn, String key, int length, int passes, int psize) {

        conn.del(key);
        for (int i = 0; i < length; i++) {
            conn.rpush(key, String.valueOf(i));
        }


        Pipeline pipeline = conn.pipelined();
        long time = System.currentTimeMillis();

        for (int p = 0; p < passes; p++) {

            for (int pi = 0; pi < psize; pi++) {
                pipeline.rpoplpush(key, key);
            }

            pipeline.sync();

        }

        return (passes * psize) / (System.currentTimeMillis() - time);


    }
}
