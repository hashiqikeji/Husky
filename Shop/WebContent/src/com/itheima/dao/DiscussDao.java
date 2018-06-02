package com.itheima.dao;

import com.itheima.domain.Discuss;
import com.itheima.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DiscussDao {

    public void inDiscuss(Discuss discuss,String username) {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="insert into discuss values(?,?,?,?,?,?,?)";
        try {
            runner.update(sql,discuss.getDid(),discuss.getArticle(),discuss.getDistime(),discuss.getUid(),discuss.getTitle(),discuss.getImage(),username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Discuss> FindAllDiscuss() throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from discuss order by distime desc";
        return runner.query(sql,new BeanListHandler<Discuss>(Discuss.class));
    }

    public void DelDiscuss(String did) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="delete from discuss where did=?";
        runner.update(sql,did);
    }
}
