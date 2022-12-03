package cn.hi.awesome.hirpc.provider.netty;

import cn.hi.awesome.hirpc.common.exception.RpcIOException;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolDecoder;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolEncoder;
import cn.hi.awesome.hirpc.provider.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyProviderServer implements RpcServer {

    @Override
    public void startServer(String ip, int port) {
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
                                    .addLast(new RpcProtocolDecoder())
                                    .addLast(new RpcProtocolEncoder())
                                    .addLast(NettyProviderHandler.getInstance());
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
