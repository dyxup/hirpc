package com.hi.awesome.hirpc.test;

import cn.hi.awesome.hirpc.annotation.RpcConsumer;

public class HelloWorldConsumer {

    @RpcConsumer
    HelloWorldService helloWorldService;

}
