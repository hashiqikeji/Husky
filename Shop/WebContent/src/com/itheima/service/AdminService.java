package com.itheima.service;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Variety;
import com.itheima.domain.Order;
import com.itheima.domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminService {
    public List<Variety> findAllVariety() throws SQLException {
        AdminDao dao=new AdminDao();
        return dao.findAllVariety();
    }

    public void saveProduct(Product product) throws SQLException {
        AdminDao dao=new AdminDao();
        dao.saveProduct(product);
    }

    public List<Order> findAllOrders() throws SQLException {
        AdminDao dao=new AdminDao();
        List<Order> orderList=dao.findAllOrders();
        return orderList;
    }

    public List<Map<String,Object>> findOrderInfoByOid(String oid) throws SQLException {

        AdminDao dao=new AdminDao();
        List<Map<String,Object>> mapList=dao.findOrderInfoByOid(oid);
        return mapList;
    }
}
