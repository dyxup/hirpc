package cn.hi.awesome.hirpc.annotation;

import cn.hi.awesome.hirpc.common.invoke.InvokeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC服务消费者注解
 * 接口名称可以扫描到
 * 限制在字段注解
 * Runtime需要解析
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcConsumer {

    /**
     * 版本号
     */
    String version() default "";

    /**
     * 分组
     */
    String group() default "";

    /**
     * 超时时间 ms
     */
    long timeout() default 3000L;

    /**
     * 调用方式
     */
    InvokeType invokeType() default InvokeType.SYNC;
}
