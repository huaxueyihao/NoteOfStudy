package com.thread.study.chapter08;

import java.io.Serializable;

public class Attachment implements Serializable {
    private static final long serialVersionUID = -1307321651009338756L;
    private String contentType;
    private byte[] content = new byte[0];

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
