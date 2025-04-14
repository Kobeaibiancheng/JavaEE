package com.spring.impl;

import com.spring.dao.UserDao;
import sun.rmi.log.LogOutputStream;

public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
        System.out.println("UserDaoImpl创建");
    }

    @Override
    public void print() {
        System.out.println("printing...");
    }

    @Override
    public void save() {
        System.out.println("save running...");
    }


    public void init(){
        System.out.println("初始化。。。");
    }


    public void destory(){
        System.out.println("销毁。。。");
    }
}
