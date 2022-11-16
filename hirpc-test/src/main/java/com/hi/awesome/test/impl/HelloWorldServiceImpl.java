package com.hi.awesome.test.impl;

import com.hi.awesome.test.HelloWorldService;
import cn.hi.awesome.hirpc.annotation.RpcProvider;

@RpcProvider(version = "1.0.0")
public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
