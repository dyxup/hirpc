package cn.hi.awesome.hirpc.provider;

import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.meta.RpcKey;

import java.util.Map;

public class ProviderHolder {

    public static Map<RpcKey<RpcProvider>, Object> providerInstanceMap;

    public static Object getProviderInstance(String className, String version, String group) {
        return providerInstanceMap.get(RpcKey.of(className, version, group));
    }

}
