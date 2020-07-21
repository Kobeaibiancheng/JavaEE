package servlet;


import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String idString = req.getParameter("id");
        int id = Integer.valueOf(idString);

        UserService userService = new UserService();
        try {
            int ret = userService.delete(id);
            if (ret != 0) {
                resp.sendRedirect("/list.html");
            }else {
                System.out.println("删除失败");
                resp.getWriter().write("<h2> 删除失败 </h2>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
