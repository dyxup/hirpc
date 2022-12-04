package cn.hi.awesome.hirpc.common.invoker;

import cn.hi.awesome.hirpc.common.exception.RpcInvokeException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionInvoker implements Invoker {
    @Override
    public <R> R invoke(String className, String methodName, Object instance, Object[] types, Object[] params) {
        try {
            Class<?> clazz = Class.forName(className);
            List<Class<?>> invokeMethodParamTypeClassList = Arrays.stream(types).map(Object::getClass).collect(Collectors.toList());
            List<Method> methodToInvoke = Arrays.stream(clazz.getMethods())
                    .filter(x -> methodName.equals(x.getName()))
                    .filter(x -> {
                        List<Class<?>> methodParamTypeClassList = Arrays.stream(x.getParameterTypes()).collect(Collectors.toList());
                        return judgeSameParamList(invokeMethodParamTypeClassList, methodParamTypeClassList);
                    })
                    .collect(Collectors.toList());
            if(methodToInvoke.size() > 1) {
                throw new RpcInvokeException("ReflectionInvoker invoke, ambiguous method found to invoke");
            }
            if(methodToInvoke.size() == 0) {
                throw new RpcInvokeException("ReflectionInvoker invoke, no method found to invoke");
            }
            return (R) methodToInvoke.get(0).invoke(instance, params);
        } catch (Exception e) {
            throw new RpcInvokeException(e);
        }
    }

    private boolean judgeSameParamList(List<Class<?>> a, List<Class<?>> b) {
        if(a.size() != b.size()) {
            return false;
        }
        boolean same = true;
        for (int i = 0; i < a.size(); i++) {
            if(!a.get(i).equals(b.get(i))) {
                same = false;
                break;
            }
        }
        return same;
    }
}
