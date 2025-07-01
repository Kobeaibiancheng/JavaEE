package beans.factory.support;

import beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String,Object> singletonObjects = new HashMap<>();


    /**
     * 获取单例bean对象
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }


    /**
     * add一个单例bean到容器中
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName,Object singletonObject){
        singletonObjects.put(beanName, singletonObject);
    }



}
