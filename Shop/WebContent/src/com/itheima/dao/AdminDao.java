package com.itheima.dao;

import com.itheima.domain.Variety;
import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminDao {

    public List<Variety> findAllVariety() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from variety";
        return runner.query(sql, new BeanListHandler<Variety>(Variety.class));
    }

    public void saveProduct(Product product) throws SQLException {

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        runner.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),
                product.getIs_hot(),product.getPdesc(),product.getPflag(),
                product.getVariety().getVid());
    }

    public List<Order> findAllOrders() throws SQLException {

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from orders";
        return runner.query(sql,new BeanListHandler<Order>(Order.class));
    }

    public List<Map<String,Object>> findOrderInfoByOid(String oid) throws SQLException {

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select p.pimage,p.pname,p.shop_price,i.count,i.subtotal "+
                " from orderitem i,product p "+
                " where i.pid=p.pid and i.oid=? ";
        return runner.query(sql,new MapListHandler(),oid);
    }
}
