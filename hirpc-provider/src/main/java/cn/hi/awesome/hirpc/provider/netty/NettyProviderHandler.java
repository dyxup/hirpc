package cn.hi.awesome.hirpc.provider.netty;

import cn.hi.awesome.hirpc.protocol.msg.RpcProtocol;
import cn.hi.awesome.hirpc.protocol.msg.RpcMsgHeader;
import cn.hi.awesome.hirpc.protocol.msg.RpcRequestMsg;
import cn.hi.awesome.hirpc.protocol.msg.RpcResponseMsg;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty处理收到的消息
 * 交给外部类调用对应的Proxy
 * 单例模式
 */
@Slf4j
public class NettyProviderHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequestMsg>> {
    private NettyProviderHandler() {}

    public static NettyProviderHandler getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final NettyProviderHandler INSTANCE = new NettyProviderHandler();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcRequestMsg> msg) throws Exception {
        log.debug("NettyProviderReceiveHandler channelRead0, {}, {}, {}, {}", msg.getBody().getClassName(), msg.getBody().getMethodName(),
                msg.getBody().getGroup(), msg.getBody().getVersion());
    }
}