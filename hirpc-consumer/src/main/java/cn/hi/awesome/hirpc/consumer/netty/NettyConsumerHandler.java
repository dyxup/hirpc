package cn.hi.awesome.hirpc.consumer.netty;

import cn.hi.awesome.hirpc.protocol.msg.*;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyConsumerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponseMsg>> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponseMsg> rpcResponseMsgRpcProtocol) throws Exception {
        log.debug("NettyConsumerHandler channelRead0 receive: {}", JSON.toJSONString(rpcResponseMsgRpcProtocol));
    }
}
