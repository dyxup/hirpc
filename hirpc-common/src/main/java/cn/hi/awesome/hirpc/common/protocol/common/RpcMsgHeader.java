package cn.hi.awesome.hirpc.common.protocol.common;

import java.io.Serializable;

/**
 * header 18byte
 */
public class RpcMsgHeader implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 2 识别魔数
     */
    private short magicNumber = 0xab;
    /**
     * 1 消息类型
     */
    private byte msgType;
    /**
     * 1 status
     */
    private byte status;
    /**
     * 8 请求id
     */
    private long reqId;
    /**
     * 1 序列化类型
     */
    private byte serialType;
    /**
     * 4 长度
     */
    private int len;

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getReqId() {
        return reqId;
    }

    public void setReqId(long reqId) {
        this.reqId = reqId;
    }

    public byte getSerialType() {
        return serialType;
    }

    public void setSerialType(byte serialType) {
        this.serialType = serialType;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
