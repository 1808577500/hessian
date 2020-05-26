package com.hhweb.hessian.annotation;

import java.lang.annotation.*;

/**
 * hessian客户端
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HessianClient {

    /**
     * 超时时间(ms)
     * 默认值取全局配置
     * 如果未配置为10s
     * @return
     */
    public int timeout() default 0;

    /**
     * 读超时时间
     * 默认值取全局配置
     * 如果未配置为10s
     * @return
     */
    public int readTimeout() default  0;

    public String serviceUrl();

    public boolean hessian2() default true;



}
