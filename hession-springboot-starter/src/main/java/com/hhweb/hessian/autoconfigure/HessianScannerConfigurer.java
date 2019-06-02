package com.hhweb.hessian.autoconfigure;

import com.hhweb.hessian.processer.HessianBeanScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;


public class HessianScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

    private HessianProperties hessianProperties;

    public HessianScannerConfigurer(HessianProperties hessianProperties) {
        this.hessianProperties = hessianProperties;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HessianBeanScanner scanner = new HessianBeanScanner( registry, hessianProperties);
        scanner.scan( hessianProperties.getBasePackage());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

}
