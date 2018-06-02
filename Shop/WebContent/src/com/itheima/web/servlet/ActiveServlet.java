package com.itheima.web.servlet;

import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ActiveServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeCode=request.getParameter("activeCode");
        UserService service=new UserService();
        try {
            service.active(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }
}
