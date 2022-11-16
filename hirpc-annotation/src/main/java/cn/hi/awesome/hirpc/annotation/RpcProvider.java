package cn.hi.awesome.hirpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC服务提供者注解
 * 接口名称可以扫描到
 * 限制在类上注解
 * Runtime需要解析
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcProvider {
    /**
     * 接口class
     */
    Class<?> interfaceClass();

    /**
     * 版本号
     */
    String version() default "1.0.0";

    /**
     * 服务分组
     */
    String group() default "default";
}
