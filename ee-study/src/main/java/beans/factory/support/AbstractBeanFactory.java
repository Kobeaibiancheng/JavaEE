package beans.factory.support;

import beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(beanDefinition);

    }

    protected abstract Object createBean(BeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String name);
}
