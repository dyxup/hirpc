package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.consumer.netty.NettyConsumerServer;

public class RpcConsumerTest {
    public static void main(String[] args) {
        NettyConsumerServer server = new NettyConsumerServer();
        server.start();
    }
}
