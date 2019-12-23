package com.thread.study.chapter03;

public class MMSCInfo {

    private final String deviceID;

    private final String url;

    private final int maxAttachmeentSizeBytes;

    public MMSCInfo(String deviceID, String url, int maxAttachmeentSizeBytes) {
        this.deviceID = deviceID;
        this.url = url;
        this.maxAttachmeentSizeBytes = maxAttachmeentSizeBytes;
    }

    public MMSCInfo(MMSCInfo prototype) {
        this.deviceID = prototype.deviceID;
        this.url = prototype.url;
        this.maxAttachmeentSizeBytes = prototype.maxAttachmeentSizeBytes;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmeentSizeBytes() {
        return maxAttachmeentSizeBytes;
    }



}
