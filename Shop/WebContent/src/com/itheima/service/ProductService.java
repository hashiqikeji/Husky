package com.itheima.service;

import com.itheima.dao.ProductDao;
import com.itheima.domain.*;
import com.itheima.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductService {

    //获得热门商品
    public List<Product> findHotProductList() {
        ProductDao dao=new ProductDao();
        List<Product> hotProductList= null;
        try {
            hotProductList = dao.findHotProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  hotProductList;
    }


    //获得最新商品
    public List<Product> findNewProductList(){
        ProductDao dao=new ProductDao();
        List<Product> newProductList= null;
        try {
            newProductList = dao.findNewProductList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProductList;
    }

    public List<Variety> findAllVariety() {
        ProductDao dao=new ProductDao();
        List<Variety> varietyList= null;
        try {
            varietyList = dao.findAllVariety();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return varietyList;
    }

    public PageBean findProductListByVid(String vid,int currentPage,int currentCount) {

        ProductDao dao=new ProductDao();

        //封装一个PageBean 返回web层
        PageBean<Product> pageBean=new PageBean<Product>();


        //封装当前页
        pageBean.setCurrentPage(currentPage);
        //封装每页显示的条数
        pageBean.setCurrentCount(currentCount);
        //封装总条数
        int totalCount=0;
        try {
            totalCount= dao.getCount(vid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setTotalCount(totalCount);

        //封装总页数
        int totalPage=(int)Math.ceil(1.0*totalCount/currentCount);
        pageBean.setTotalPage(totalPage);

        //当前页显示的数据
        //当前页与起始索引index的关系
        int index=(currentPage-1)*currentCount;
        List<Product> list=null;
        try {
          list=dao.findProductBypage(vid,index,currentCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setList(list);
        return pageBean;
    }

    public Product findProductByPid(String pid) {
        ProductDao dao=new ProductDao();
        Product product= null;
        try {
            product =dao.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


    //提交订单将订单的数据和订单项的数据存储到数据库中
    public void submitOrder(Order order) throws SQLException{

        ProductDao dao=new ProductDao();


        try {
            //1开启事务
            DataSourceUtils.startTransaction();
            //2 调用dao存储order表数据的方法
            dao.addOrders(order);
            //3.调用dao存储orderitem表数据的方法
            dao.addOrderItem(order);
        } catch (SQLException e) {
            DataSourceUtils.rollback();
            e.printStackTrace();
        }finally {
            DataSourceUtils.commitAndRelease();
        }

    }

    public void updateOrderAdrr(Order order) {
        ProductDao dao=new ProductDao();
        dao.updateOrderAdrr(order);
    }

    public void updateOrderState(String r6_Order) {
        ProductDao dao=new ProductDao();
        dao.updateOrderState(r6_Order);
    }

    public List<Order> findAllOrders(String uid) {

        ProductDao dao=new ProductDao();
        List<Order> orderList= null;
        try {
            orderList = dao.findAllOrders(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public List<Map<String, Object>> findAllOrderItemByOid(String oid) {

        ProductDao dao=new ProductDao();
        List<Map<String, Object>> mapList=dao.findAllOrderItemByOid(oid);

        return mapList;

    }

    public List<Product> findAllProduct() throws SQLException {
        ProductDao dao=new ProductDao();
        List<Product> allProductList=dao.findAllProduct();
        return allProductList;
    }

    public void delProduct(String pid) throws SQLException {
        ProductDao dao=new ProductDao();
        dao.delProduct(pid);
    }

    public void delVariety(String vid) throws SQLException {
        ProductDao dao=new ProductDao();
        dao.delVariety(vid);
    }
}
