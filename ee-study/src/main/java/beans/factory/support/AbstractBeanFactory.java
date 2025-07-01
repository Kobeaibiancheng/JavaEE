package beans.factory.support;

import beans.factory.BeanFactory;
import beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);

    }

    /**
     * 抽象方法获取bean
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;



    /**
     * 抽象方法创建bean
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;



}
