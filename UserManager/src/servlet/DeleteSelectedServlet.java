package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String[] idGroup = req.getParameterValues("id[]");
        UserService userService = new UserService();


        int sum = 0;
        for (int i = 0; i < idGroup.length; i++) {
            int j = Integer.parseInt(idGroup[i]);
            try {
                int delete = userService.delete(j);
                sum += delete;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Map<String,Object> return_map = new HashMap<>();
        if (sum == idGroup.length) {
            return_map.put("msg",true);
        }else {
            return_map.put("msg",false);
        }

        //把登录成功的map返回给前端。json      : 便于前端进行处理。
        ObjectMapper objectMapper = new ObjectMapper();
        //就是将returnMap，转换为json字符串
        objectMapper.writeValue(resp.getWriter(),return_map);
    }
}
