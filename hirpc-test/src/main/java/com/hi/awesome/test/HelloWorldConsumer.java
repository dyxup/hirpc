package com.hi.awesome.test;

import cn.hi.awesome.hirpc.annotation.RpcConsumer;
import cn.hi.awesome.hirpc.scan.ClassFileScanner;
import cn.hi.awesome.hirpc.scan.ConsumerScanner;
import cn.hi.awesome.hirpc.scan.JarClassScanner;
import cn.hi.awesome.hirpc.scan.ProviderScanner;

import java.util.ArrayList;
import java.util.List;

public class HelloWorldConsumer {

    @RpcConsumer
    HelloWorldService helloWorldService;

    public static void main(String[] args) throws Exception {
        ClassFileScanner classFileScanner = new ClassFileScanner("com.hi.awesome.test");
        JarClassScanner jarClassScanner = new JarClassScanner("com.hi.awesome.test");
        List<String> classNameList = new ArrayList<>();
        classNameList.addAll(classFileScanner.scan());
        classNameList.addAll(jarClassScanner.scan());
        ConsumerScanner consumerScanner = new ConsumerScanner(classNameList);
        ProviderScanner providerScanner = new ProviderScanner(classNameList);
        System.out.println(consumerScanner.scan());
        System.out.println(providerScanner.scan());
    }

}
