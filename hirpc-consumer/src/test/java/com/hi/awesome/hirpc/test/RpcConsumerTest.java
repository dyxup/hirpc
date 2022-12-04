package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.consumer.netty.NettyConsumerServer;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolDecoder;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolEncoder;

public class RpcConsumerTest {
    public static void main(String[] args) {
        NettyConsumerServer server = new NettyConsumerServer("127.0.0.1", 7766, NettyConsumerTestHandler.getInstance(),
                new RpcProtocolEncoder(), new RpcProtocolDecoder());
        server.start();
    }
}
