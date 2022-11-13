package cn.hi.awesome.hirpc;

import cn.hi.awesome.hirpc.annotation.RpcConsumer;
import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.scan.ClassScanner;

import java.lang.reflect.Field;
import java.util.List;

public class Consumer {
    @RpcConsumer(version = "1.0.0")
    HelloWorldService helloWorldService;

    public void printResult() {
        System.out.println(helloWorldService.sayHello("Lily"));
    }

    public static void main(String[] args) throws Exception {
        List<String> classNameList = ClassScanner.getClassNameList("cn.hi.awesome");
        classNameList.forEach(c -> {
            try {
                Class<?> aClass = Class.forName(c);
                System.out.println(c);
                System.out.println(aClass.getDeclaredAnnotation(RpcProvider.class));
                for (Field field : aClass.getDeclaredFields()) {
                    System.out.println(field.getAnnotation(RpcConsumer.class));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

}
