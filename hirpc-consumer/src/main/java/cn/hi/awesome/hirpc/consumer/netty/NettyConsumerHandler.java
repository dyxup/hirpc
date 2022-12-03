package cn.hi.awesome.hirpc.consumer.netty;

import cn.hi.awesome.hirpc.protocol.msg.*;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyConsumerHandler extends SimpleChannelInboundHandler<RpcMsg<RpcResponseMsg>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // TODO: test code
        log.debug("NettyConsumerHandler channelActive");
        RpcMsg<RpcRequestMsg> msg = new RpcMsg<>();
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
        log.debug("NettyConsumerHandler channelActive send, {}", JSON.toJSONString(msg));
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMsg<RpcResponseMsg> rpcResponseMsgRpcMsg) throws Exception {
        log.debug("NettyConsumerHandler channelRead0 receive: {}", JSON.toJSONString(rpcResponseMsgRpcMsg));
    }
}
