package com.hi.awesome.hirpc.test.impl;

import com.hi.awesome.hirpc.test.HelloWorldService;
import cn.hi.awesome.hirpc.annotation.RpcProvider;

@RpcProvider(interfaceClass = HelloWorldService.class, version = "1.0.0")
public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
