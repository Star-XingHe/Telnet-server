package com.company.dao;

import com.company.bean.User;
import com.sun.corba.se.spi.monitoring.StatisticMonitoredAttribute;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection = null;


    public UserDAO()  {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("未找到相关数据库驱动");
            e.printStackTrace();
        }

    }
    public Connection getConnection()throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/loginregister?characterEncoding=UTF-8&serverTimezone=UTC",
                "root","757620");
    }

    public void add(User user)
    {
        String sql = "insert into user values(null,?,?)";
        try(Connection c = getConnection();                 //获取自动增加的id号
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        )
        {
            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                user.setId(rs.getInt(1));
            }

        } catch (SQLException throwables) {
            System.out.print("add操作出错");
            throwables.printStackTrace();
        }
    }
    public User get(int id)
    {
        User user = null;
        try(Connection c= getConnection();
            Statement s = c.createStatement();
        ) {
            String sql = "select *from user where id ="+id;
            ResultSet rs = s.executeQuery(sql);
            if(rs.next())
            {
                user = new User();
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException throwables) {
            System.out.print("get操作失败");
            throwables.printStackTrace();
        }
        return user;
    }
    public List<User> list()
    {
        return list(0,Short.MAX_VALUE);
    }
    public List<User>list(int start,int count){
        List<User> users = new ArrayList<>();
        String sql = "select *from user order by id desc limit ?,?";
        try(Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            System.out.println("List操作出错");
            throwables.printStackTrace();
        }
        return users;
    }
    public Boolean find(User user)
    {
        Boolean isfind =false;
        String sql = "select *from user where name= '"+user.getName()+"'and password='"+ user.getPassword()+"'";
        try(
                Connection c = getConnection();
                Statement s = c.createStatement();)
        {
            ResultSet rs=s.executeQuery(sql);
            if(rs.next())
            {
                isfind = true;
            }

        } catch (SQLException throwables) {
            System.out.println("find操作失败");
            throwables.printStackTrace();
        }
        return isfind;
    }

}
