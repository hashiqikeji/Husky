package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

import java.sql.SQLException;

public class UserService {
    public boolean regist(User user){
        UserDao dao=new UserDao();
        int row= 0;
        try {
            row = dao.regist(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row>0?true:false;
    }

    public void active(String activeCode) throws SQLException {
        UserDao dao=new UserDao();
        dao.active(activeCode);
    }

    //校验用户名是否存在
    public boolean checkUsername(String username) throws SQLException {
        UserDao dao=new UserDao();
        Long isExist=0L;
        isExist=dao.checkUsername(username);
        return isExist>0?true:false;
    }

    //用户登陆的方法
    public User login(String username,String password) throws SQLException{
        UserDao dao=new UserDao();
        return dao.login(username,password);
    }

}
