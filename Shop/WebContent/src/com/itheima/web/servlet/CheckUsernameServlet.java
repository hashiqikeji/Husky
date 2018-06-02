package com.itheima.web.servlet;

import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得用户名
        String username=request.getParameter("username");
        UserService service=new UserService();
        boolean isExist= false;
        try {
            isExist = service.checkUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json="{\"isExist\":"+isExist+"}";
        response.getWriter().write(json);
    }
}
