package cn.hi.awesome.hirpc.consumer.netty;

import cn.hi.awesome.hirpc.consumer.RpcConsumerServer;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolDecoder;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolEncoder;
import cn.hi.awesome.hirpc.protocol.msg.RpcProtocol;
import cn.hi.awesome.hirpc.protocol.msg.RpcResponseMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

public class NettyConsumerServer implements RpcConsumerServer {

    private final String ip;

    private final int port;

    private final SimpleChannelInboundHandler<RpcProtocol<RpcResponseMsg>> handler;

    private final MessageToByteEncoder<RpcProtocol<?>> encoder;

    private final MessageToMessageDecoder<ByteBuf> decoder;

    public NettyConsumerServer(String ip, int port, SimpleChannelInboundHandler<RpcProtocol<RpcResponseMsg>> handler, MessageToByteEncoder<RpcProtocol<?>> encoder, MessageToMessageDecoder<ByteBuf> decoder) {
        this.ip = ip;
        this.port = port;
        this.handler = handler;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup(4);
        try {
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(encoder)
                                    .addLast(decoder)
                                    .addLast(handler);
                        }
                    });
            bootstrap.connect(ip, port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
