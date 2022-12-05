package cn.hi.awesome.hirpc.provider.netty;

import cn.hi.awesome.hirpc.common.invoker.Invoker;
import cn.hi.awesome.hirpc.common.invoker.ReflectionInvoker;
import cn.hi.awesome.hirpc.meta.RpcKey;
import cn.hi.awesome.hirpc.protocol.msg.RpcProtocol;
import cn.hi.awesome.hirpc.protocol.msg.RpcMsgHeader;
import cn.hi.awesome.hirpc.protocol.msg.RpcRequestMsg;
import cn.hi.awesome.hirpc.protocol.msg.RpcResponseMsg;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import cn.hi.awesome.hirpc.provider.ProviderHolder;
import cn.hi.awesome.hirpc.provider.proxy.GeneralProviderProxy;
import cn.hi.awesome.hirpc.provider.proxy.ProviderProxy;
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

    private ProviderProxy proxy = new GeneralProviderProxy();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcRequestMsg> msg) throws Exception {
        log.debug("NettyProviderReceiveHandler channelRead0, {}, {}, {}, {}", msg.getBody().getClassName(), msg.getBody().getMethodName(),
                msg.getBody().getGroup(), msg.getBody().getVersion());
        // 1. 读取调用参数 调用方法
        RpcRequestMsg body = msg.getBody();
        Object result = proxy.proxy(body.getClassName(), body.getVersion(), body.getGroup(), body.getMethodName(), body.getParamTypes(), body.getParams());

        // 2. 组装结果
        // TODO: factory
        RpcProtocol<RpcResponseMsg> resp = new RpcProtocol<>();
        msg.getHeader().setMsgType(RpcMsgType.RESPONSE.getCode());
        resp.setHeader(msg.getHeader());
        RpcResponseMsg responseMsg = new RpcResponseMsg();
        responseMsg.setResponse(result);
        resp.setBody(responseMsg);

        // 3. 回传
        channelHandlerContext.writeAndFlush(resp);
    }
}
