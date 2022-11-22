package cn.hi.awesome.hirpc.common.protocol.common;

public enum RpcMsgType {
    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEART_BEAT((byte) 3);
    private final byte code;
    RpcMsgType(byte code) {
        this.code = code;
    }
    public byte getCode() {
        return code;
    }
}
