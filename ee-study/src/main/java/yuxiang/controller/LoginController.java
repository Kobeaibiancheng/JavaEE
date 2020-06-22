package yuxiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yuxiang.model.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/1")
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
    @RequestMapping(value = "/login")
    public String login(){
        return "/login.html";
    }

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    @ResponseBody
    public Object login2(@RequestParam("username") String username1,
                         String password,
                         String password2){
        System.out.println(username1+"="+password+", "+password2);
        User user = new User();
        user.setUsername("烤鸭");
        user.setPassword("123");
        user.setBirthday(new Date());
        return user;
    }

    @RequestMapping(value = "/login3", method = RequestMethod.POST)
    @ResponseBody
    public Object login3(User u){
        System.out.println(u);
        User user = new User();
        user.setUsername("烤鸭");
        user.setPassword("123");
        user.setBirthday(new Date());
        return user;
    }

    @RequestMapping(value = "/login4",method = RequestMethod.POST)
    @ResponseBody
    public Object login4(@RequestBody User u) {
        System.out.println(u);
        User user = new User();
        user.setUsername("北京");
        user.setPassword("123");
        user.setBirthday(new Date());
        return user;
    }

    @RequestMapping(value = "/login5",method = RequestMethod.POST)
    @ResponseBody
    public Object login5(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestBody User u) {
        if ("abc".equals(u.getUsername())&&"123".equals(u.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",u);
        }
        System.out.println(u);
        User user = new User();
        user.setUsername("北京");
        user.setPassword("123");
        user.setBirthday(new Date());
        return user;
    }
}
