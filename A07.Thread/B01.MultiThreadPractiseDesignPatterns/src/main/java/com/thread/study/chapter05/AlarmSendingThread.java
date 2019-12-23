package com.thread.study.chapter05;

import com.thread.study.chapter04.AlarmAgent;
import com.thread.study.chapter04.AlarmInfo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmSendingThread extends AbstractTerminateableThread {

    private final AlarmAgent alarmAgent = new AlarmAgent();

    // 告警对列
    private final BlockingQueue<AlarmInfo> alarmQueue;
    private final ConcurrentMap<String, AtomicInteger> submittedAlarmRegistry;

    public AlarmSendingThread() {
        alarmQueue = new ArrayBlockingQueue<>(100);
        submittedAlarmRegistry = new ConcurrentHashMap<>();
        alarmAgent.init();
    }


    @Override
    protected void doRun() throws Exception {
        AlarmInfo alarm = alarmQueue.take();

        terminationToken.reservations.decrementAndGet();

        try {
            // 将告警信息发送至告警服务器
            alarmAgent.sendAlarm(alarm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 处理回复告警：将相应的故障告警从注册表中删除，使得相应故障回复后若再次出现相同故障
         * 该故障信息能能够上报到服务器
         */
        if (AlarmType.RESUME == alarm.type) {
            String key = AlarmType.FAULT.toString() + ":" + alarm.getId() + "@" + alarm.getExtraInfo();
            submittedAlarmRegistry.remove(key);
            key = AlarmType.RESUME.toString() + ":" + alarm.getId() + "@" + alarm.getExtraInfo();
            submittedAlarmRegistry.remove(key);
        }

    }


    public int sendAlarm(final AlarmInfo alarmInfo) {
        AlarmType type = alarmInfo.type;
        String id = alarmInfo.getId();
        String extraInfo = alarmInfo.getExtraInfo();

        if (terminationToken.isToShutdown()) {
            // 记录警告
            System.out.println("rejected alarm:" + id + "," + extraInfo);
            return -1;
        }
        int duplicateSubmissionCount = 0;

        try {
            AtomicInteger prevSubmittedCounter;
            prevSubmittedCounter = submittedAlarmRegistry.putIfAbsent(type.toString() + ":" + id + "@" + extraInfo, new AtomicInteger(0));
            if (null == prevSubmittedCounter) {
                terminationToken.reservations.incrementAndGet();
                alarmQueue.put(alarmInfo);
            } else {
                // 故障未回复，不用重复发送告警信息给服务器，故仅增加计数
                duplicateSubmissionCount = prevSubmittedCounter.incrementAndGet();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return duplicateSubmissionCount;

    }

    @Override
    protected void doCleanup(Exception exp) {
        if (null != exp && !(exp instanceof InterruptedException)) {
            exp.printStackTrace();
        }
        alarmAgent.disconnect();
    }

    public void terminate() {

    }
}
