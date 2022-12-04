package cn.hi.awesome.hirpc.provider;

import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.common.exception.RpcInitException;
import cn.hi.awesome.hirpc.meta.RpcKey;
import cn.hi.awesome.hirpc.provider.register.RpcServerRegister;
import cn.hi.awesome.hirpc.scan.ClassFileScanner;
import cn.hi.awesome.hirpc.scan.JarClassScanner;
import cn.hi.awesome.hirpc.scan.ProviderScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RpcServer入口类
 */
public class RpcServerInit {

    private final RpcServerConfig rpcServerConfig = RpcServerConfig.read();

    private final RpcProviderServer rpcServer;

    private final RpcServerRegister rpcServerRegister;

    public RpcServerInit(RpcProviderServer rpcServer, RpcServerRegister rpcServerRegister) {
        this.rpcServer = rpcServer;
        this.rpcServerRegister = rpcServerRegister;
    }

    /** INIT RESULT **/
    private boolean init = false;
    private final List<String> providerClassNameList = new ArrayList<>();
    private Map<RpcKey<RpcProvider>, Object> proxyMap;

    public void doInit() {
        try {
            init();
            // TODO: start at another thread
            rpcServer.start();
            // TODO: register after start
            rpcServerRegister.register();
            // TODO: unregister when exception
//            rpcServerRegister.unregister();
            init = true;
        } catch (Exception e) {
            throw new RuntimeException("RpcServerInit: doInit failed, exception:", e);
        }
    }

    public Object getHandler(String interfaceName, String version, String group) {
        if(!init) {
            throw new RpcInitException("RpcServerInit: must invoke <doInit> first");
        }
        return proxyMap.get(RpcKey.of(interfaceName, version, group));
    }

    private void init() {
        ClassFileScanner classFileScanner = new ClassFileScanner(rpcServerConfig.getScanPackage());
        JarClassScanner jarClassScanner = new JarClassScanner(rpcServerConfig.getScanPackage());
        providerClassNameList.addAll(classFileScanner.scan());
        providerClassNameList.addAll(jarClassScanner.scan());

        ProviderScanner providerScanner = new ProviderScanner(providerClassNameList);
        proxyMap = providerScanner.scan();

        // create real handler instance
        proxyMap.entrySet().forEach(e -> {
            // TODO: Instantiation
//            e.setValue();
//            new RpcServerInstanceInstantiation();
        });
    }
}
