package service;

import dao.UserDao;
import entiy.PageBean;
import entiy.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserService {

    public User login(User loginUser) throws SQLException {
        UserDao userDao = new UserDao();
        User user = userDao.login(loginUser);
        return user;
    }


    public int add(User user) throws SQLException {
        UserDao userDao = new UserDao();
        int ret = userDao.add(user);
        return ret;
    }


    public int delete(int id) throws SQLException {
        UserDao userDao = new UserDao();
        int ret = userDao.delete(id);
        return ret;
    }


    public User select(int id) throws SQLException {
        UserDao userDao = new UserDao();
        User user = userDao.select(id);
        return user;
    }

    public int update(User updateUser) throws SQLException {
        UserDao userDao = new UserDao();
        int ret = userDao.update(updateUser);
        return ret;
    }



    public PageBean<User> findAllByPage(int currentPage, int rows, Map<String, String[]> map) throws SQLException {
        PageBean<User> pageBean=new PageBean<>();
        UserDao userDao=new UserDao();
        int totalPage;
        int record = userDao.findAllRecord(map);//查询共有多少条记录
        //总共的页数 totalPage
        if(record % rows==0){
            totalPage = record / rows;
        }else{
            totalPage = record / rows+1;
        }
        int start=(currentPage-1)*rows;
        List<User> users = userDao.findByPage(start, rows, map);
        pageBean.setCurrentPage(currentPage);
        pageBean.setList(users);
        pageBean.setRows(rows);
        pageBean.setTotalCount(record);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }


}
