package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.protocol.msg.*;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyConsumerTestHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponseMsg>> {

    private NettyConsumerTestHandler() {}

    public static NettyConsumerTestHandler getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final NettyConsumerTestHandler INSTANCE = new NettyConsumerTestHandler();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("NettyConsumerHandler channelActive");
        RpcProtocol<RpcRequestMsg> msg = new RpcProtocol<>();
        RpcMsgHeader rpcMsgHeader = new RpcMsgHeader();
        rpcMsgHeader.setReqId(111L);
        rpcMsgHeader.setMsgType(RpcMsgType.REQUEST.getCode());
        rpcMsgHeader.setMagicNumber(RpcMsgConstants.RPC_MSG_HEADER_MAGIC_NUM);
        rpcMsgHeader.setStatus((byte) 1);
        // 序列化后填充len

        msg.setHeader(rpcMsgHeader);
        RpcRequestMsg rpcRequestMsg = new RpcRequestMsg();
        rpcRequestMsg.setClassName("a");
        rpcRequestMsg.setGroup("b");
        msg.setBody(rpcRequestMsg);
        log.debug("NettyConsumerTestHandler channelActive send, {}", JSON.toJSONString(msg));
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponseMsg> rpcResponseMsgRpcProtocol) throws Exception {
        log.debug("NettyConsumerTestHandler channelRead0 receive: {}", JSON.toJSONString(rpcResponseMsgRpcProtocol));
    }
}
