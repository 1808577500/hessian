package com.hhweb.hessian.autoconfigure;

import com.hhweb.hessian.processer.HessianServiceBeanProcesser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty(
        prefix = HessianProperties.HESSIAN_PREFIX,
        name = {"enabled"},
        matchIfMissing = true
)
@EnableConfigurationProperties( HessianProperties.class)
public class HessianAutoConfiguration implements EnvironmentAware {

    private HessianProperties properties;

    public HessianAutoConfiguration() {
    }

//    @Bean
//    public HessianServiceBeanProcesser hessianServiceBeanProcesser() {
//        return new HessianServiceBeanProcesser( properties);
//    }

    @Bean
    public HessianScannerConfigurer hessianScannerConfigurer() {
        return new HessianScannerConfigurer( properties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get( environment);
        properties = binder.bind( HessianProperties.HESSIAN_PREFIX, Bindable.of( HessianProperties.class)).get();
    }
}
