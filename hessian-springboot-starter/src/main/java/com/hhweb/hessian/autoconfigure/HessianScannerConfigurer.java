package com.hhweb.hessian.autoconfigure;

import com.hhweb.hessian.processer.HessianBeanScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.EventListener;


public class HessianScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

    private HessianProperties hessianProperties;
    private Environment environment;

    public HessianScannerConfigurer(HessianProperties hessianProperties, Environment environment) {
        this.hessianProperties = hessianProperties;
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HessianBeanScanner scanner = new HessianBeanScanner( registry, hessianProperties, environment);
        scanner.scan(  StringUtils.tokenizeToStringArray( hessianProperties.getBasePackage(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

}
