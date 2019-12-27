package com.jdk8.study.Queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Delayed {

    /**
     * 延迟时间
     */
    private final long delayTime;

    // 到期时间
    private final long expire;

    // 数据
    private String data;

    public DelayedTask(long delayTime, String data) {
        this.delayTime = delayTime;
        this.data = data;
        this.expire = System.currentTimeMillis() + delayTime;
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "DelayedTask{" +
                "delayTime=" + delayTime +
                ", expire=" + expire +
                ", data='" + data + '\'' +
                '}';
    }
}
