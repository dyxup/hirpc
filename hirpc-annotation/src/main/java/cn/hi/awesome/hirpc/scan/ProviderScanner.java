package cn.hi.awesome.hirpc.scan;

import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.common.exception.ScanException;
import cn.hi.awesome.hirpc.meta.RpcKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * interfaceName --> ProviderInstance
 *
 */
public class ProviderScanner implements Scanner<Map<RpcKey<RpcProvider>, Object>> {

    private final List<String> classNameList;

    public ProviderScanner(List<String> classNameList) {
        this.classNameList = classNameList;
    }

    @Override
    public Map<RpcKey<RpcProvider>, Object> scan() {
        Map<RpcKey<RpcProvider>, Object> result = new HashMap<>();
        try {
            for (String className : classNameList){
                Class<?> aClass = Class.forName(className);
                RpcProvider rpcProvider = aClass.getDeclaredAnnotation(RpcProvider.class);
                if(rpcProvider == null) {
                    continue;
                }
                RpcKey<RpcProvider> key = RpcKey.of(rpcProvider.interfaceClass().getName(), rpcProvider.version(), rpcProvider.group(), rpcProvider);
                // TODO: create instance
                Object instance = new Object();
                result.put(key, instance);
            }
        } catch (Exception e) {
            throw new ScanException(e);
        }
        return result;
    }

}
