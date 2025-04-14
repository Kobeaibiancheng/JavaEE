package com.spring.factory;

import com.spring.dao.UserDao;
import com.spring.impl.UserDaoImpl;

/**
 * 工厂实例方法实例化
 */
public class DynamicFactory {
    public  UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
