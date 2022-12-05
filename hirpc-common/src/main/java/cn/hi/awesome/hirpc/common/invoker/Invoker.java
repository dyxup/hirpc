package cn.hi.awesome.hirpc.common.invoker;

import java.lang.reflect.Method;

public interface Invoker {
    default <R> R invoke(String className, String methodName, Object instance, String[] types, Object[] params){return null;}
    default <R> R invoke(Class<?> clazz, String methodName, Object[] types, Object[] params){return null;}
    default <R> R invoke(Method method, Object[] types, Object[] params){return null;}
}
