package com.thread.study.chapter04;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class AlarmAgent {

    // 用于记录AlarmAgent是否连接上告警服务器
    private volatile boolean connectedToServer = false;

    // 模式角色：GuardedSuspension.Predicate
    private final Predicate agentConnected = new Predicate() {
        @Override
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    // 模式角色：GuardedSuspension.Blocker
    private final Blocker blocker = new ConditionVarBlocker();

    // 心跳定时器
    private final Timer heartbeatTimer = new Timer(true);


    public void sendAlarm(final AlarmInfo alarm) throws Exception {

        // 可能需要等待，知道AlarmAgent连接上告警服务器(或者链接中断后重新连上服务器)
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            @Override
            public Void call() throws Exception {
                doSendAlarm(alarm);
                return null;
            }
        };
        blocker.callWithGuard(guardedAction);
    }

    private void doSendAlarm(AlarmInfo alarm) {
        // 省略其他代码
        System.out.println("sending alarm " + alarm);
        try {
            // 模拟发送告警至服务器
            Thread.sleep(50);
        } catch (Exception e) {

        }
    }


    public void init() {
        // 告警链接线程
        Thread connectionThread = new Thread(new ConnectingTask());
        connectionThread.start();
        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        System.out.println("disconnected from alarm server");
        connectedToServer = false;
    }

    protected void onDisconnected() {
        connectedToServer = false;
    }

    // 负责与告警服务器建立网络连接
    public void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectedToServer = true;
                    System.out.println("connected to server");
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ConnectingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ;
            }
            onConnected();
        }
    }

    /**
     * 心跳定时任务：定时检查与告警服务器的连接是否正常，发现连接异常后自动重新连接
     */
    private class HeartbeatTask extends TimerTask {
        @Override
        public void run() {

            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private void reconnect() {
            ConnectingTask connectingThread = new ConnectingTask();
            connectingThread.run();
        }

        private boolean testConnection() {
            //
            return true;
        }
    }
}
