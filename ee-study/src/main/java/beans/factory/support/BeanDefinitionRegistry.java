package beans.factory.support;

import beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
