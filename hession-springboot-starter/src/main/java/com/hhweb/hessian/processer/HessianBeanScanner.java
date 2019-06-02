package com.hhweb.hessian.processer;

import com.hhweb.hessian.annotation.HessianClient;
import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.hessian.autoconfigure.HessianProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类扫描器
 */
@Slf4j
public class HessianBeanScanner extends ClassPathBeanDefinitionScanner {

    private HessianProperties hessianProperties;

    public HessianBeanScanner(BeanDefinitionRegistry registry, HessianProperties hessianProperties) {
        super(registry, false);
        this.hessianProperties = hessianProperties;
        addIncludeFilter( new AnnotationTypeFilter( HessianClient.class));
        addIncludeFilter( new AnnotationTypeFilter( HessianService.class));
    }

    @Override
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {

        AnnotationMetadata metadata = ((ScannedGenericBeanDefinition)definitionHolder.getBeanDefinition()).getMetadata();
        if( metadata.isAnnotated( HessianService.class.getName())){
            BeanDefinitionReaderUtils.registerBeanDefinition( definitionHolder, registry);
            HessianService hessianService = null;
            try {

                Class beanClass = Class.forName( metadata.getClassName());
                hessianService = (HessianService) beanClass.getAnnotation(HessianService.class);

                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setFactoryBeanName( hessianService.path());
                beanDefinition.setBeanClass( HessianServiceExporter.class);
                beanDefinition.getPropertyValues().add("service", ((DefaultListableBeanFactory) registry).getBean( definitionHolder.getBeanName()));
                beanDefinition.getPropertyValues().add("serviceInterface", beanClass.getInterfaces()[0]);
                BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, beanDefinition.getFactoryBeanName());
                BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
                //构造Mapping
            }catch (Exception e){
                logger.error( "create HessianServiceExporter[" + hessianService.path() +"] bean error,plase check your code.", e);
            }
        }else {
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setFactoryBeanName( definitionHolder.getBeanName());
            genericBeanDefinition.setBeanClass( HessianProxyFactoryBean.class);
            genericBeanDefinition.getPropertyValues()
                    .add( "serviceInterface", definitionHolder.getBeanDefinition().getBeanClassName())
                    .add("connectTimeout", hessianProperties.getTimeout())
                    .add("readTimeout", hessianProperties.getReadTimeout());
            try {
                HessianClient hessianClient = Class.forName( definitionHolder.getBeanDefinition().getBeanClassName()).getAnnotation( HessianClient.class);
                genericBeanDefinition.getPropertyValues().add( "serviceUrl", hessianClient.serviceUrl());
                if( hessianClient.readTimeout() > 0){
                    genericBeanDefinition.getPropertyValues().add("readTimeout", hessianProperties.getReadTimeout());
                }
                if( hessianClient.timeout() > 0){
                    genericBeanDefinition.getPropertyValues().add("connectTimeout", hessianProperties.getTimeout());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(genericBeanDefinition, genericBeanDefinition.getFactoryBeanName());
            BeanDefinitionReaderUtils.registerBeanDefinition( beanDefinitionHolder, registry);
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return ( metadata.hasAnnotation( HessianClient.class.getName()) && metadata.isInterface())
                ||
                ( metadata.hasAnnotation( HessianService.class.getName()) && super.isCandidateComponent( beanDefinition));
    }
}
