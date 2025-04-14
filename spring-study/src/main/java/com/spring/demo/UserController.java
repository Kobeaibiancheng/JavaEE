package com.spring.demo;

import com.spring.impl.UserServiceImpl;
import com.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {


    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applications.xml");
        UserService userService = (UserServiceImpl)app.getBean("userService");

        userService.save();

    }
}
