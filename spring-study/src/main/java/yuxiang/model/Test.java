package yuxiang.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Test implements BeanNameAware, BeanFactoryAware,ApplicationContextAware,BeanPostProcessor,
        BeanDefinitionRegistryPostProcessor,InitializingBean,DisposableBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory: (BeanFactoryAware)");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName: (BeanNameAware)");

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext: (ApplicationContextAware)");

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry: (BeanDefinitionRegistryPostProcessor)");

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization: (BeanPostProcessor)");

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization: (BeanPostProcessor)");

        return bean;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory: (BeanFactoryPostProcessor)");

    }

    @Override
    public void destroy() throws Exception {//Bean 的销毁方法
        System.out.println("destroy: (DisposableBean)");

    }

    @Override
    public void afterPropertiesSet() throws Exception {//Bean 的初始化方法：Bean 调用构造方法创建好后，就调用初始化方法
        System.out.println("afterPropertiesSet: (InitializingBean)");

    }
}
