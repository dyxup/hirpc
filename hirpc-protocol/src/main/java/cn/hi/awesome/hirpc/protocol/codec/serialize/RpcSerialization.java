package cn.hi.awesome.hirpc.protocol.codec.serialize;

public interface RpcSerialization {

    <T> byte[] serialize(T o);

    <T> T deserialize(byte[] data);
}