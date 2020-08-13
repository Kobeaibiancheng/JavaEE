package dao;

import entiy.User;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public int add(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            String sql = "insert into usermessage(name,username,password,position,gender,age,address,qq,email) " +
                    "values (?,?,?,?,?,?,?,?,?)";
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,user.getName());
            ps.setString(2,user.getUserName());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getPosition());
            ps.setString(5,user.getGender());
            ps.setInt(6,user.getAge());
            ps.setString(7,user.getAddress());
            ps.setString(8,user.getQq());
            ps.setString(9,user.getEmail());

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
    public int delete(int id) throws SQLException {
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
    public User select(int id) throws SQLException {
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
    public int update(User updateUser) throws SQLException {
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


    /**
     * 查询条件可以随机
     * 可以任意组合
     * @param start
     * @param rows
     * @param map
     * @return
     */
    public static List<User> findByPage(int start, int rows, Map<String, String[]> map) throws SQLException {

        List<User> userList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;


        String sql = "select * from usermessage where 1 = 1";

        StringBuffer sb = new StringBuffer(sql);

        List<Object> list = new ArrayList<>();

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String value = map.get(key)[0];
            if (value != null && !"".equals(value)) {
                sb.append(" and ").append(key).append(" like ? ");
                list.add("%" + value + "%");
            }
        }

        sb.append(" limit ?,? ");
        list.add(start);
        list.add(rows);

        System.out.println(sb);
        System.out.println(list);
        try{
            connection  = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sb.toString());

            setValues(ps,list.toArray());
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPosition(resultSet.getString("position"));
                user.setGender(resultSet.getString("gender"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                user.setQq(resultSet.getString("qq"));
                user.setEmail(resultSet.getString("email"));
                userList.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return userList;
    }

    public static void setValues(PreparedStatement ps, Object[] arrays) throws SQLException {

        for (int i = 0; i < arrays.length; i++) {
            ps.setObject(i+1,arrays[i]);
        }

    }


    /**
     * 查询共有多少条数据
     * @param map
     * @return
     * @throws SQLException
     */
    public int findAllRecord(Map<String, String[]> map) throws SQLException {
        String sql="select count(*) from usermessage where 1=1";
        StringBuilder s=new StringBuilder();
        s.append(sql);
        Set<String> keySet = map.keySet();
        List<Object> list=new ArrayList<>();
        for(String key:keySet){
            String value=map.get(key)[0];
            if(value!=null && !"".equals(value)){
                //有值
                s.append(" and ").append(key).append(" like ?");
                list.add("%"+value+"%");
            }
        }
        System.out.println("findAllRecord::sql:" + s);
        System.out.println("findAllRecord::list:"+list);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(s.toString());
            setValues(ps,list.toArray());
            resultSet = ps.executeQuery();
            if(resultSet.next()){
                count = resultSet.getInt(1); //对总记录数赋值 等价于rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,ps,resultSet);
        }
        return count;
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
        //System.out.println(login(user));
        //System.out.println(add(user));
        //System.out.println(select(5));
        //System.out.println(update(user));
        Map<String, String[]> map = new HashMap<>();
        String[] names = {""};
        map.put("name",names);
        String[] address = {"陕西"};
        map.put("address",address);
        List<User> list = findByPage(0,5,map);
        for (User u : list) {
            System.out.println(u);
        }
    }
}
