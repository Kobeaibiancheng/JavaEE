package com.spring.factory;

import com.spring.dao.UserDao;
import com.spring.impl.UserDaoImpl;

/**
 * 对象工厂,通过静态工厂获得对象
 */
public class StaticFactory {


    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

}
