package cn.hi.awesome.hirpc.provider.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty处理收到的消息
 * 交给外部类调用对应的Proxy
 * 单例模式
 */
@Slf4j
public class NettyReceiveHandler extends SimpleChannelInboundHandler<String> {
    private NettyReceiveHandler() {}

    public static NettyReceiveHandler getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final NettyReceiveHandler INSTANCE = new NettyReceiveHandler();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("receive: {}", s);
        channelHandlerContext.writeAndFlush(s);
    }
}
