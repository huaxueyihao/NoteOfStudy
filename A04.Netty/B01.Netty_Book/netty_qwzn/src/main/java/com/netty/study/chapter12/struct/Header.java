package com.netty.study.chapter12.struct;

import java.util.HashMap;
import java.util.Map;

public final class Header {

    private int crcCode = 0xabef0101;

    private int length; // 消息长度

    private long sessionID; //会话ID

    private byte type; // 消息类型

    private byte proprity;// 消息优先级

    private Map<String,Object> attachment = new HashMap<String, Object>();


    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getProprity() {
        return proprity;
    }

    public void setProprity(byte proprity) {
        this.proprity = proprity;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", proprity=" + proprity +
                ", attachment=" + attachment +
                '}';
    }
}
