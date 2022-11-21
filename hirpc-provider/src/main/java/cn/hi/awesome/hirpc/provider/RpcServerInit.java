package cn.hi.awesome.hirpc.provider;

import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.common.exception.RpcInitException;
import cn.hi.awesome.hirpc.meta.RpcKey;
import cn.hi.awesome.hirpc.scan.ClassFileScanner;
import cn.hi.awesome.hirpc.scan.JarClassScanner;
import cn.hi.awesome.hirpc.scan.ProviderScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RpcServerInit {

    private final RpcServerConfig rpcServerConfig = RpcServerConfig.read();

    private final RpcServer rpcServer;

    private final RpcServerRegister rpcServerRegister;

    public RpcServerInit(RpcServer rpcServer, RpcServerRegister rpcServerRegister) {
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
            rpcServer.startServer(rpcServerConfig.getListenAddr(), rpcServerConfig.getRegistryPort());
            rpcServerRegister.register();
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
//            e.setValue();
            new RpcServerInstanceInstantiation();
        });
    }
}
