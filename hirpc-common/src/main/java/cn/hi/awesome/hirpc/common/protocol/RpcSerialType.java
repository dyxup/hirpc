package cn.hi.awesome.hirpc.common.protocol;

public enum RpcSerialType {
    JSON((byte) 1);
    private final byte code;
    RpcSerialType(byte code) {
        this.code = code;
    }
    public byte getCode() {
        return code;
    }
}
