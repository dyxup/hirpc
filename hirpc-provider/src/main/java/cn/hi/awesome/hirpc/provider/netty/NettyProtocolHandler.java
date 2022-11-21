package cn.hi.awesome.hirpc.provider.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NettyProtocolHandler extends SimpleChannelInboundHandler<String> {

    // 单例模式

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("receive: {}", s);
        channelHandlerContext.writeAndFlush(s);
    }
}
