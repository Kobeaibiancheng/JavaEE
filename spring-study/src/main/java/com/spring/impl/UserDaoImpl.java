package com.spring.impl;

import com.spring.dao.UserDao;
import com.spring.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class UserDaoImpl implements UserDao {

    //基本数据类型
    private String username;
    private int age;


    //集合数据类型
    private List<String> list;
    private Map<String, User> userMap;
    private Properties properties;

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*public UserDaoImpl() {
        System.out.println("UserDaoImpl创建");
    }*/

    @Override
    public void print() {
        System.out.println("printing...");
    }

    @Override
    public void save() {
        //System.out.println(this.username + "=====" + this.age);
        System.out.println(list);
        System.out.println(userMap);
        System.out.println(properties);
        System.out.println("save running...");
    }


    /*public void init(){
        System.out.println("初始化。。。");
    }


    public void destory(){
        System.out.println("销毁。。。");
    }*/
}
