package com.thread.study.chapter06;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FTPClientUtil {

    private final FTPClient ftp = new FTPClient();

    private final Map<String, Boolean> dirCreaateMap = new HashMap<>();

    private FTPClientUtil() {

    }

    public static Future<FTPClientUtil> newInstance(String ftpServer, String userName, String password) {

        Callable<FTPClientUtil> callable = new Callable<FTPClientUtil>() {
            @Override
            public FTPClientUtil call() throws Exception {
                FTPClientUtil self = new FTPClientUtil();
                self.init(ftpServer, userName, password);
                return self;
            }
        };

        FutureTask<FTPClientUtil> task = new FutureTask<>(callable);
        /**
         * 新建的线程相当于模式角色：Promise，TaskExecutor。
         */
        new Thread(task).start();
        return task;
    }

    private void init(String ftpServer, String userName, String password) {
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);

        int reply;
        ftp.connect(ftpServer);

        System.out.println(ftp.getReplyString());

        reply = ftp.getReplyCode();

        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection");
        }

        boolean isOK = ftp.login(userName,password);


    }

    public void upload(File file) {

    }
}
