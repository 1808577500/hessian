package com.hhweb.hessian.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HessianService {
    /**
     * 发布路径
     * 根据系统配置，会添加路径前缀
     * @return
     */
    String path();

    /**
     * 描述
     * @return
     */
    String description() default "";
}
