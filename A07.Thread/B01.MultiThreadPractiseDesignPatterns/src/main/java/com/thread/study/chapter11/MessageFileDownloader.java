package com.thread.study.chapter11;


import com.thread.study.chapter05.AbstractTerminateableThread;
import com.thread.study.chapter06.FTPClient;
import com.thread.study.chapter06.FTPClientConfig;
import com.thread.study.chapter06.FTPReply;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.*;

public class MessageFileDownloader {

    // 模式角色：SerialThreadConfiment.WorkerThread
    private final WorkerThread workerThread;

    public MessageFileDownloader(String outputDir, final String ftpServer, final String userName, final String password) {
        this.workerThread = new WorkerThread(outputDir, ftpServer, userName, password);
    }

    public void init() {
        workerThread.start();
    }

    public void shutdown() {
        workerThread.terminate();
    }

    public void downloadFile(String file) {
        workerThread.download(file);
    }

    // 模式角色：SerialThreadConfiment.WorkerThread
    private static class WorkerThread extends AbstractTerminateableThread {

        // 模式角色：SerialThreadConfiment.Queue;
        private final BlockingQueue<String> workQueue;
        private final Future<FTPClient> ftpClientPromise;
        private final String outputDir;

        public WorkerThread(String outputDir, final String ftpServer, final String userName, final String password) {
            this.workQueue = new ArrayBlockingQueue<>(100);
            this.outputDir = outputDir + "/";

            this.ftpClientPromise = new FutureTask<FTPClient>(new Callable<FTPClient>() {
                @Override
                public FTPClient call() throws Exception {
                    FTPClient ftpClient = initFTPClient(ftpServer, userName, password);
                    return ftpClient;
                }
            });
            new Thread((FutureTask<FTPClient>) ftpClientPromise).start();
        }

        public void download(String file) {
            try {
                workQueue.put(file);
                terminationToken.reservations.incrementAndGet();
            } catch (InterruptedException e) {

            }
        }


        @Override
        protected void doRun() throws Exception {
            String file = workQueue.take();

            OutputStream os = null;

            try {
                os = new BufferedOutputStream(new FileOutputStream(outputDir + file));
                ftpClientPromise.get().retrieveFile(file, os);
            } finally {
                if (null != os) {
                    try {
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                terminationToken.reservations.decrementAndGet();
            }
        }

        @Override
        protected void doCleanup(Exception cause) {
            try {
                ftpClientPromise.get().disconnect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        private FTPClient initFTPClient(String ftpServer, String userName, String password) throws Exception {
            FTPClient ftpClient = new FTPClient();

            FTPClientConfig config = new FTPClientConfig();
            ftpClient.configure(config);

            int reply;
            ftpClient.connect(ftpServer);

            System.out.println(ftpClient.getReplyString());
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new RuntimeException("FTP server refused connection. ");
            }

            boolean isOK = ftpClient.login(userName, password);
            if (isOK) {
                System.out.println(ftpClient.getReplyString());
            } else {
                throw new RuntimeException("Failed to login. " + ftpClient.getReplyString());
            }

            reply = ftpClient.cwd("~/messages");
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new RuntimeException("Failed to change working directory.replay: " + reply);
            } else {
                System.out.println(ftpClient.getReplyString());
            }

//            ftpClient.setFileType(FTP.AS)

            return ftpClient;
        }


    }


}
