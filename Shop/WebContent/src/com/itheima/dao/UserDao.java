package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDao {
    public int regist(User user) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
        int update=runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),
                user.getName(),user.getEmail(),user.getTelephone(), user.getBirthday(),
                user.getSex(),user.getState(),user.getCode());
        return update;
    }

    //激活
    public void active(String activeCode) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update user set state=? where code=?";
        runner.update(sql,1,activeCode);
    }

    //校验用户名是否存在
    public Long checkUsername(String username) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select count(*) from user where username=?";
        Object query=runner.query(sql,new ScalarHandler(),username);
        return (Long) query;
    }

    //用户登陆的方法
    public User login(String username,String password) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from user where username=? and password=?";
        return runner.query(sql, new BeanHandler<User>(User.class),username,password);
    }

}
