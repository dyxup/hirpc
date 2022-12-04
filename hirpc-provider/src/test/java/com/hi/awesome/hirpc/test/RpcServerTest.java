package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolDecoder;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolEncoder;
import cn.hi.awesome.hirpc.provider.netty.NettyProviderServer;

public class RpcServerTest {
    public static void main(String[] args) {
        NettyProviderServer server = new NettyProviderServer("127.0.0.1", 7766, NettyProviderTestHandler.getInstance(), new RpcProtocolDecoder(), new RpcProtocolEncoder());
        server.start();
    }
}
