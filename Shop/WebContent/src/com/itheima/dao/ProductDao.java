package com.itheima.dao;

import com.itheima.domain.Variety;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductDao {

    //获得热门商品
    public List<Product> findHotProductList() throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from product where is_hot=? limit ?,?";
        return runner.query(sql,new BeanListHandler<Product>(Product.class),1,0,9);
    }

    public List<Product> findNewProductList() throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from product order by pdate desc limit ?,?";
        return runner.query(sql,new BeanListHandler<Product>(Product.class),0,12);
    }

    public List<Variety> findAllVariety() throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from variety ";
        return runner.query(sql, new BeanListHandler<Variety>(Variety.class));


    }

    public int getCount(String vid) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select count(*) from product where vid=?";
        Long query= (Long)runner.query(sql,new ScalarHandler(),vid);
        return query.intValue();
    }

    public List<Product> findProductBypage(String vid, int index, int currentCount) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from product where vid=? limit ?,?";
        List<Product> list=runner.query(sql,new BeanListHandler<Product>(Product.class),vid,index,currentCount);
        return list;
    }

    public Product findProductByPid(String pid) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from product where pid=?";
        return runner.query(sql,new BeanHandler<Product>(Product.class),pid);
    }


    //向orders表插入数据
    public void addOrders(Order order) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        Connection conn=DataSourceUtils.getConnection();
        runner.update(conn,sql,order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
                order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());

    }

    //向orderitem表插入数据
    public void addOrderItem(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Connection conn = DataSourceUtils.getConnection();
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item:orderItems){
            runner.update(conn,sql,item.getItemid(),item.getCount(),item.getSubtotal(),
                           item.getProduct().getPid(),item.getOrder().getOid());
        }

    }

    public void updateOrderAdrr( Order order) {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update orders set address=?,name=?,telephone=? where oid=?";
        try {
            runner.update(sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderState(String r6_Order) {

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update orders set state=? where oid=?";
        try {
            runner.update(sql,1,r6_Order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAllOrders(String uid) throws SQLException {

        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from orders where uid=?";
        return runner.query(sql,new BeanListHandler<Order>(Order.class),uid);
    }

    public List<Map<String, Object>> findAllOrderItemByOid(String oid) {

        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?";
        List<Map<String,Object>> mapList= null;
        try {
            mapList = runner.query(sql,new MapListHandler(),oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public List<Product> findAllProduct() throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="select * from product";
        return runner.query(sql,new  BeanListHandler<Product>(Product.class));
    }

    public void delProduct(String pid) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="delete from product where pid=?";
        String sql_1="delete from orderitem where pid=?";
        Connection conn=DataSourceUtils.getConnection();
        runner.update(conn,sql_1,pid);
        runner.update(conn,sql,pid);
    }

    public void delVariety(String vid) throws SQLException {
        QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
        String sql="delete from product where vid=?";
        String sql_1="delete from variety where vid=?";
        Connection conn=DataSourceUtils.getConnection();
        runner.update(conn,sql,vid);
        runner.update(conn,sql_1,vid);
    }
}
