package com.thread.study.chapter11;

public class SampleClient {

    private static final MessageFileDownloader DOWNLOADER;

    static {
        DOWNLOADER = new MessageFileDownloader("/home/viscent/tmp/incoming","192.168.1.105","datacenter","abc123");
        DOWNLOADER.init();
    }

    public static void main(String[] args) {
        DOWNLOADER.downloadFile("abc.xml");
    }


}
