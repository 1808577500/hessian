package com.hhweb.hessian.processer;

import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.hessian.autoconfigure.HessianProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

public class HessianServiceBeanProcesser implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private HessianProperties hessianProperties;

    private ApplicationContext applicationContext;

    //RequestMappingHandlerMapping
    private final Method detectHandlerMethodsMethod;

    public HessianServiceBeanProcesser(HessianProperties hessianProperties) {
        this.hessianProperties = hessianProperties;
        detectHandlerMethodsMethod = ReflectionUtils.findMethod( RequestMappingHandlerMapping.class, "detectHandlerMethods", Object.class);
        detectHandlerMethodsMethod.setAccessible( true);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       Map<String,Object> applicationMap = event.getApplicationContext().getBeansWithAnnotation( HessianService.class);
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ( applicationContext.getAutowireCapableBeanFactory());

//        RequestMappingHandlerMapping mappingHandlerMapping =  event.getApplicationContext().getBean( RequestMappingHandlerMapping.class);

       applicationMap.forEach( ( k, v) -> {

       });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
