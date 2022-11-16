package cn.hi.awesome.hirpc.scan;

import cn.hi.awesome.hirpc.annotation.RpcConsumer;
import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.common.exception.ScanException;
import cn.hi.awesome.hirpc.meta.RpcKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerScanner implements Scanner<Map<RpcKey<RpcConsumer>, Object>>{

    private final List<String> classNameList;

    public ConsumerScanner(List<String> classNameList) {
        this.classNameList = classNameList;
    }

    @Override
    public Map<RpcKey<RpcConsumer>, Object> scan() {
        Map<RpcKey<RpcConsumer>, Object> result = new HashMap<>();
        try {
            for (String className :classNameList){
                Class<?> aClass = Class.forName(className);
                Arrays.stream(aClass.getDeclaredFields()).forEach(field -> {
                    RpcConsumer rpcConsumer = field.getAnnotation(RpcConsumer.class);
                    if(rpcConsumer == null) {
                        return;
                    }
                    // TODO: cache proxy instance for rpc
                    Object instance = new Object();
                    result.put(RpcKey.of(field.getType().getName(), rpcConsumer.version(), rpcConsumer.group(), rpcConsumer), instance);
                });
            }
        } catch (Exception e) {
            throw new ScanException(e);
        }
        return result;
    }
}
