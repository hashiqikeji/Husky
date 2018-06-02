package com.itheima.web.servlet;

import com.google.gson.Gson;
import com.itheima.domain.Variety;
import com.itheima.domain.Order;
import com.itheima.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminServlet")
public class AdminServlet extends BaseServlet {


    //根据订单id查询订单项和商品信息
    public void findOrderInfoByOid(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获得oid
        String oid=request.getParameter("oid");

        AdminService service=new AdminService();
        List<Map<String,Object>> mapList=service.findOrderInfoByOid(oid);

        Gson gson=new Gson();
        String json=gson.toJson(mapList);

       // System.out.println(json);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

    }


    //获得所有的订单
    public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        //获得所有的订单信息----List<Order>
        AdminService service=new AdminService();
        List<Order> orderList=service.findAllOrders();
        request.setAttribute("orderList", orderList);

        request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
    }


    public void findAllVariety(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        //提供一个List<Variety> 转成json字符串
        AdminService service=new AdminService();
        List<Variety> varietyList=service.findAllVariety();

        Gson gson=new Gson();
        String json=gson.toJson(varietyList);
        response.setContentType("text/json;charset=UTF-8");

        response.getWriter().write(json);
    }

    public void admin_user(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if (username.equals("admin")&&password.equals("admin123")){
            request.setAttribute("username",username);
            HttpSession session=request.getSession();
            session.setAttribute("admin_0",username);
            request.getRequestDispatcher("admin/home.jsp").forward(request,response);
        }
        else {
            response.sendRedirect("default.jsp");
        }
    }
}
