package cn.hi.awesome.hirpc.provider.proxy;

import cn.hi.awesome.hirpc.common.invoker.Invoker;
import cn.hi.awesome.hirpc.common.invoker.ReflectionInvoker;
import cn.hi.awesome.hirpc.provider.ProviderHolder;

public class GeneralProviderProxy implements ProviderProxy {

    private Invoker invoker = new ReflectionInvoker();

    @Override
    public Object proxy(String className, String version, String group, String methodName, String[] types, Object[] params) {
        Object instance = ProviderHolder.getProviderInstance(className, version, group);

        return invoker.invoke(className, methodName, instance, types, params);
    }
}
