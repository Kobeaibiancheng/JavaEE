package yuxiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * login.html index.html 那些url可以访问
     *
     *
     * index.html  1.访问/   SpringBoot 默认的首页 (转发)
     *             2.访问 index.html
     * login.html  1.login.html
     *             2./login  --> 通过LoginController 中的方法 (转发)
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login.html";
    }
}
