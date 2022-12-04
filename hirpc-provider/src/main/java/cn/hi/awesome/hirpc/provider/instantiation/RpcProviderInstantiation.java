package cn.hi.awesome.hirpc.provider.instantiation;

/**
 * 获取服务提供者实例
 * 使用场景是协议解析完毕时找到对应的服务bean
 */
public interface RpcProviderInstantiation {
    /**
     * byClass查找
     * @param className 类名
     * @return 实例
     */
    Object getProviderInstance(String className);
}
