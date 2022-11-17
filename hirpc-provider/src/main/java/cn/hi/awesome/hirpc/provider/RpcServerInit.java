package cn.hi.awesome.hirpc.provider;

import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.meta.RpcKey;
import cn.hi.awesome.hirpc.scan.ClassFileScanner;
import cn.hi.awesome.hirpc.scan.JarClassScanner;
import cn.hi.awesome.hirpc.scan.ProviderScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RpcServerInit implements RpcServer {

    private RpcServerConfig rpcServerConfig;

    /** INIT RESULT **/
    private final List<String> providerClassNameList = new ArrayList<>();
    private Map<RpcKey<RpcProvider>, Object> handlerMap;

    @Override
    public void startServer() {
        init();
        register();
    }

    public Object getHandler(String interfaceName, String version, String group) {
        return handlerMap.get(RpcKey.of(interfaceName, version, group));
    }

    private void init() {
        rpcServerConfig = RpcServerConfig.read();

        ClassFileScanner classFileScanner = new ClassFileScanner(rpcServerConfig.getScanPackage());
        JarClassScanner jarClassScanner = new JarClassScanner(rpcServerConfig.getScanPackage());
        providerClassNameList.addAll(classFileScanner.scan());
        providerClassNameList.addAll(jarClassScanner.scan());

        ProviderScanner providerScanner = new ProviderScanner(providerClassNameList);
        handlerMap = providerScanner.scan();

        // create real handler instance
        handlerMap.entrySet().forEach(e -> {
//            e.setValue();
            new RpcServerInstanceInstantiation();
        });
    }

    private void register() {

    }
}
