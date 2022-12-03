package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.provider.netty.NettyProviderServer;

public class RpcServerTest {
    public static void main(String[] args) {
        NettyProviderServer server = new NettyProviderServer();
        server.startServer("127.0.0.1", 7766);

    }
}
