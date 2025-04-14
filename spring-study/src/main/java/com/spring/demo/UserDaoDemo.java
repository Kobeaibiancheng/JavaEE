package com.spring.demo;

import com.spring.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoDemo {
    public static void main(String[] args) {

        //1.导入Spring的坐标，在pom.xml中
        //2.创建Bean,一个类实现一个接口，重新接口中的方法
        //3.创建applications.xml,在里面指定bean的id
        //4.通过ApplicationContext对象来getBean(Bean的id)
        ApplicationContext app = new ClassPathXmlApplicationContext("applications.xml");
        UserDao userDao = (UserDao) app.getBean("userDao");
        userDao.print();
    }
}
