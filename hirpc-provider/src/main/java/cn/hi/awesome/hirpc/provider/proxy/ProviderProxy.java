package cn.hi.awesome.hirpc.provider.proxy;

public interface ProviderProxy {
    Object proxy(String className, String version, String group, String methodName,
                 String[] types, Object[] params);
}
