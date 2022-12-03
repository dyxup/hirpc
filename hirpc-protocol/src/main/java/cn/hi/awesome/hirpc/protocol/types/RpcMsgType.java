package cn.hi.awesome.hirpc.protocol.types;

public enum RpcMsgType {
    REQUEST((short) 1),
    RESPONSE((short) 2),
    HEART_BEAT((short) 3);
    private final short code;
    RpcMsgType(short code) {
        this.code = code;
    }
    public short getCode() {
        return code;
    }

    public static RpcMsgType getByCode(short code) {
        switch (code) {
            case 1: return REQUEST;
            case 2: return RESPONSE;
            case 3: return HEART_BEAT;
        }
        return null;
    }
}
