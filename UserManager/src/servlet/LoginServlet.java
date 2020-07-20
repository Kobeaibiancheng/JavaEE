package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entiy.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=utf-8"); //设置响应数据的数据格式和编

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username " + username);
        System.out.println("password" + password);


        Writer writer = resp.getWriter();


        User loginUser = new User();
        loginUser.setUserName(username);
        loginUser.setPassword(password);


        UserService userService = new UserService();//创建service层的对象


        Map<String,Object> return_map = new HashMap<>(); //创建一个map集合，存放返回到客户端的数据


        System.out.println(loginUser);
        System.out.println("======================");


        try {
            User user = userService.login(loginUser);
            System.out.println(user);
            if (user != null) {
                System.out.println("登陆成功！" + username);
                //说明数据库中有，显示登录成功，把登录信息存入session
                req.getSession().setAttribute("user",user);
                return_map.put("msg",true);
            }else {
                System.out.println("failure");
                writer.write("<h2> 登陆失败 </h2>");

                return_map.put("msg",false);
            }


            ObjectMapper mapper = new ObjectMapper(); //利用Jackson将map转化为json对象
            mapper.writeValue(resp.getWriter(),return_map);//返回给前端


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
