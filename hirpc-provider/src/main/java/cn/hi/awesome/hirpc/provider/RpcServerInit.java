package cn.hi.awesome.hirpc.provider;

import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolDecoder;
import cn.hi.awesome.hirpc.protocol.codec.RpcProtocolEncoder;
import cn.hi.awesome.hirpc.provider.instantiation.RpcProviderInstantiation;
import cn.hi.awesome.hirpc.provider.instantiation.SpringRpcProviderInstantiation;
import cn.hi.awesome.hirpc.provider.netty.NettyProviderHandler;
import cn.hi.awesome.hirpc.provider.netty.NettyProviderServer;
import cn.hi.awesome.hirpc.provider.register.RpcProviderRegister;
import cn.hi.awesome.hirpc.provider.register.ZookeeperRpcProviderRegister;
import cn.hi.awesome.hirpc.scan.ClassFileScanner;
import cn.hi.awesome.hirpc.scan.JarClassScanner;
import cn.hi.awesome.hirpc.scan.ProviderScanner;

import java.util.ArrayList;
import java.util.List;

/**
 * RpcServer入口类
 */
public class RpcServerInit {

    private final RpcProviderServer rpcServer = new NettyProviderServer("127.0.0.1", 7766, NettyProviderHandler.getInstance(), new RpcProtocolDecoder(), new RpcProtocolEncoder());

    private final RpcProviderRegister rpcProviderRegister = new ZookeeperRpcProviderRegister();

    private final RpcProviderInstantiation rpcProviderInstantiation = new SpringRpcProviderInstantiation();



    /** INIT RESULT **/
    private boolean init = false;
    private final List<String> providerClassNameList = new ArrayList<>();

    public void doInit() {
        try {
            init();
            // TODO: start at another thread
            rpcServer.start();
            // TODO: register after start
            rpcProviderRegister.register();
            // TODO: unregister when exception
//            rpcServerRegister.unregister();
            init = true;
        } catch (Exception e) {
            throw new RuntimeException("RpcServerInit: doInit failed, exception:", e);
        }
    }

    private void init() {
        ProviderServerConfig providerServerConfig = ProviderServerConfig.read();
        // TODO: 根据配置实例化fields

        ClassFileScanner classFileScanner = new ClassFileScanner(providerServerConfig.getScanPackage());
        JarClassScanner jarClassScanner = new JarClassScanner(providerServerConfig.getScanPackage());
        providerClassNameList.addAll(classFileScanner.scan());
        providerClassNameList.addAll(jarClassScanner.scan());

        ProviderScanner providerScanner = new ProviderScanner(providerClassNameList);
        // 扫描注解创建key
        ProviderHolder.providerInstanceMap = providerScanner.scan();

        // 实例化创建value 即provider的实例
        ProviderHolder.providerInstanceMap.entrySet().forEach(e -> {
            Object instance = rpcProviderInstantiation.getProviderInstance(e.getKey().getInterfaceName());
            e.setValue(instance);
        });
    }
}
