package cn.hi.awesome.hirpc.provider.instantiation;

import cn.hi.awesome.hirpc.common.exception.RpcInvokeException;
import cn.hi.awesome.hirpc.common.spring.SpringContextHolder;

public class SpringRpcProviderInstantiation implements RpcProviderInstantiation {
    @Override
    public Object getProviderInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return SpringContextHolder.applicationContext.getBean(clazz);
        } catch (Exception e) {
            throw new RpcInvokeException("SpringRpcProviderInstantiation getInstance error, ", e);
        }
    }
}
