import cn.hi.awesome.hirpc.provider.netty.NettyRpcServer;

public class RpcServerTest {
    public static void main(String[] args) {
        NettyRpcServer server = new NettyRpcServer();
        server.startServer("127.0.0.1", 7766);

    }
}
