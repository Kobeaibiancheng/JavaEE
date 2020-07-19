package servlet;

import dao.UserDao;
import entiy.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username " + username);
        System.out.println("password" + password);


        Writer writer = resp.getWriter();
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        System.out.println(user);
        System.out.println("======================");
        try {
            user = UserDao.login(user);
            System.out.println(user);
            if (user != null) {
                resp.sendRedirect("list.html");
            }else {
                System.out.println("failure");
                writer.write("<h2> 登陆失败 </h2>");
                resp.sendRedirect("login.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
