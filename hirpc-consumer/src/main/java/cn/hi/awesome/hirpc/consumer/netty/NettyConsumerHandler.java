package cn.hi.awesome.hirpc.consumer.netty;

import cn.hi.awesome.hirpc.protocol.msg.RpcProtocol;
import cn.hi.awesome.hirpc.protocol.msg.RpcRequestMsg;
import cn.hi.awesome.hirpc.protocol.msg.RpcResponseMsg;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;


@Slf4j
public class NettyConsumerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponseMsg>> {

    /**
     * 为了主动发送数据 需要持有Channel
     */
    private Channel channel;

    private SocketAddress remoteAddr;

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        remoteAddr = ctx.channel().remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponseMsg> rpcResponseMsgRpcProtocol) throws Exception {
        log.debug("NettyConsumerHandler channelRead0 receive: {}", JSON.toJSONString(rpcResponseMsgRpcProtocol));
    }

    public void send(RpcProtocol<RpcRequestMsg> protocol) {
        log.debug("NettyConsumerHandler send: {}", JSON.toJSONString(protocol));
        channel.writeAndFlush(protocol);
    }
}
