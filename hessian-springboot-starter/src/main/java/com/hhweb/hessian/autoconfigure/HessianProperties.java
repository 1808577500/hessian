package com.hhweb.hessian.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = HessianProperties.HESSIAN_PREFIX)
public class HessianProperties {

    public static final String HESSIAN_PREFIX = "hessian";

    /**
     * 超时时间(ms)
     */
    private int timeout = 100000;

    /**
     * 连接超时时间(ms)
     */
    private int readTimeout = 10000;

    private String contextPath = "/remoting";

    /**
     * 包名以(,;\t\n)等符号分割
     */
    private String basePackage = "";

}
