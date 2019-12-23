package com.thread.study.chapter05;

import com.thread.study.chapter04.AlarmInfo;

/**
 * 告警功能入口
 */
public class AlarmMgr {

    // 保存AlarmMgr类的唯一实例
    private static final AlarmMgr INSTSNCE = new AlarmMgr();

    private volatile boolean shutdownRequested = false;

    // 告警发送线程
    private final AlarmSendingThread alarmSendingThread;

    private AlarmMgr() {
        this.alarmSendingThread = new AlarmSendingThread();
    }

    public int sendAlarm(AlarmType type, String id, String extraInfo) {
        System.out.println("Trigger alarm " + type + ", " + id + "," + extraInfo);
        int duplicateSubmissionCount = 0;

        try {
            AlarmInfo alarmInfo = new AlarmInfo(id, type);
            alarmInfo.setExtraInfo(extraInfo);
            duplicateSubmissionCount = alarmSendingThread.sendAlarm(alarmInfo);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return duplicateSubmissionCount;
    }

    public synchronized void shutdown() {
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested");
        }
        alarmSendingThread.terminate();
        shutdownRequested = true;
    }


}
