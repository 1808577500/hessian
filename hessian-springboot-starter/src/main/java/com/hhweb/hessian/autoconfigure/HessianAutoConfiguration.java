package com.hhweb.hessian.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.bind.PropertySourcesPropertyValues;
//import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

@Configuration
@ConditionalOnProperty(
        prefix = HessianProperties.HESSIAN_PREFIX,
        name = {"enabled"},
        matchIfMissing = true
)
@EnableConfigurationProperties( HessianProperties.class)
public class HessianAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    private HessianProperties properties;

    public HessianAutoConfiguration() {
    }

    @Bean
    public HessianScannerConfigurer hessianScannerConfigurer() {
        return new HessianScannerConfigurer( properties, environment);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        Binder binder = Binder.get( environment);
        properties = binder.bind( HessianProperties.HESSIAN_PREFIX, Bindable.of( HessianProperties.class)).get();

    }
}
