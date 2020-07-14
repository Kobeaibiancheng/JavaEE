package dao;

import entiy.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 登陆
     * @param loginUser
     * @return
     */
    public static User login(User loginUser) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        ResultSet resultSet = null;


        try{
            String sql = "select * from usermessage where username = ? and password = ? ";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,loginUser.getUserName());
            ps.setString(2,loginUser.getPassword());
            System.out.println(loginUser.getUserName());
            System.out.println(loginUser.getPassword());
            resultSet = ps.executeQuery();


            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setGender(resultSet.getString("gender"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                user.setQq(resultSet.getString("qq"));
                user.setEmail(resultSet.getString("email"));
            }/*else {
                return null;
            }*/
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return user;
    }

    /**
     * 添加用户
     * @param user
     * @return
     * @throws SQLException
     */
    public static int add(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            String sql = "insert into usermessage(name,username,password,gender,age,address,qq,email) " +
                    "values (?,?,?,?,?,?,?,?)";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,user.getName());
            ps.setString(2,user.getUserName());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getGender());
            ps.setInt(5,user.getAge());
            ps.setString(6,user.getAddress());
            ps.setString(7,user.getQq());
            ps.setString(8,user.getEmail());

            int ret = ps.executeUpdate();
            return ret;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return 0;
    }


    /**
     * 删除用户
     * @param id
     * @return
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;


        try{
            String sql = "delete from usermessage where id = ?";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);


            int ret = ps.executeUpdate();
            return ret;

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return 0;
    }


    /**
     * 查询
     * @param id
     * @return
     */
    public static User select(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;

        try{
            String sql = "select * from usermessage where id = ?";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setGender(resultSet.getString("gender"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                user.setQq(resultSet.getString("qq"));
                user.setEmail(resultSet.getString("email"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return user;
    }

    /**
     * 更新用户信息
     * @param updateUser
     * @return
     * @throws SQLException
     */
    public static int update(User updateUser) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        User user = null;


        try{
            String sql = "update usermessage set name = ?,age = ?,gender = ?,address = ?,qq = ?,email = ? " +
                    "where id = ?";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,updateUser.getName());
            ps.setInt(2,updateUser.getAge());
            ps.setString(3,updateUser.getGender());
            ps.setString(4,updateUser.getAddress());
            ps.setString(5,updateUser.getQq());
            ps.setString(6,updateUser.getEmail());
            ps.setInt(7,updateUser.getId());

            int ret = ps.executeUpdate();
            return ret;

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return 0;
    }


    public static void main(String[] args) throws SQLException {
        User user = new User();
        user.setId(8);
        user.setUserName("wangyuxiang");
        user.setPassword("123");
        user.setQq("1244838148");
        user.setEmail("wyx160413@yeah.net");
        user.setAddress("陕西");
        user.setAge(21);
        user.setGender("男");
        user.setName("汪玉祥");
        System.out.println(login(user));
        //System.out.println(add(user));
        //System.out.println(select(5));
        System.out.println(update(user));
    }
}
