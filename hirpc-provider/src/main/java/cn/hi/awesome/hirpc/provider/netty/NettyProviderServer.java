package cn.hi.awesome.hirpc.provider.netty;

import cn.hi.awesome.hirpc.common.exception.RpcIOException;
import cn.hi.awesome.hirpc.protocol.msg.RpcProtocol;
import cn.hi.awesome.hirpc.protocol.msg.RpcRequestMsg;
import cn.hi.awesome.hirpc.provider.RpcProviderServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyProviderServer implements RpcProviderServer {

    private final String ip;

    private final int port;

    private final SimpleChannelInboundHandler<RpcProtocol<RpcRequestMsg>> handler;

    private MessageToMessageDecoder<ByteBuf> decoder;

    private MessageToByteEncoder<RpcProtocol<?>> encoder;

    public NettyProviderServer(String ip, int port, SimpleChannelInboundHandler<RpcProtocol<RpcRequestMsg>> handler, MessageToMessageDecoder<ByteBuf> decoder, MessageToByteEncoder<RpcProtocol<?>> encoder) {
        this.ip = ip;
        this.port = port;
        this.handler = handler;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override
    public void start() {
        try {
            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(decoder)
                                    .addLast(encoder)
                                    .addLast(handler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(ip, port).sync();
            log.info("NettyRpcServer: server started, {}:{}", ip, port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RpcIOException("NettyRpcServer startServer: ", e);
        }
    }

}
