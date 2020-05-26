package com.hhweb.hessian.processer;

import com.hhweb.hessian.annotation.HessianClient;
import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.hessian.autoconfigure.HessianProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;


/**
 * 类扫描器
 */
@Slf4j
public class HessianBeanScanner extends ClassPathBeanDefinitionScanner {

    private HessianProperties hessianProperties;

    private Environment environment;

    public HessianBeanScanner(BeanDefinitionRegistry registry, HessianProperties hessianProperties, Environment environment) {
        super(registry, false);
        this.hessianProperties = hessianProperties;
        this.environment = environment;
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
                hessianService = (HessianService) beanClass.getAnnotation( HessianService.class);

                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition( HessianServiceExporter.class);
                String path = hessianProperties.getContextPath() + hessianService.path();
                int char0 = path.charAt(0);
                if( char0 != '/'){
                    path += "/";
                }else if( char0 == path.charAt( 1) && char0 == '/'){
                    path = path.substring( 1);
                }
                beanDefinitionBuilder.addPropertyReference( "service", definitionHolder.getBeanName());
                beanDefinitionBuilder.addPropertyValue( "serviceInterface", beanClass.getInterfaces()[0]);
                BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(  beanDefinitionBuilder.getBeanDefinition(), path);
                BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
                //构造Mapping
            }catch (Exception e){
                logger.error( "create HessianServiceExporter[" + hessianService.path() +"] bean error,plase check your code.", e);
            }
        }else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition( HessianProxyFactoryBean.class);
            beanDefinitionBuilder.addPropertyValue( "serviceInterface", definitionHolder.getBeanDefinition().getBeanClassName());
            beanDefinitionBuilder.addPropertyValue("connectTimeout", hessianProperties.getTimeout());
            beanDefinitionBuilder.addPropertyValue("readTimeout", hessianProperties.getReadTimeout());

            try {
                HessianClient hessianClient = Class.forName( definitionHolder.getBeanDefinition().getBeanClassName()).getAnnotation( HessianClient.class);
                beanDefinitionBuilder.addPropertyValue( "serviceUrl", environment.resolvePlaceholders( hessianClient.serviceUrl()));
                beanDefinitionBuilder.addPropertyValue( "hessian2", hessianClient.hessian2());
                if( hessianClient.readTimeout() > 0){
                    beanDefinitionBuilder.addPropertyValue("readTimeout", hessianClient.readTimeout());
                }
                if( hessianClient.timeout() > 0){
                    beanDefinitionBuilder.addPropertyValue("connectTimeout", hessianClient.timeout());
                }
            } catch (ClassNotFoundException e) {
                logger.error( "create HessianProxyFactoryBean[" + definitionHolder.getBeanDefinition().getBeanClassName() +"] bean error,plase check your code.", e);
            }
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder( beanDefinitionBuilder.getBeanDefinition(), definitionHolder.getBeanName());
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
