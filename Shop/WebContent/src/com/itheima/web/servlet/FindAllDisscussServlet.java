package com.itheima.web.servlet;

import com.itheima.domain.Discuss;
import com.itheima.service.DiscussService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "FindAllDisscussServlet")
public class FindAllDisscussServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
//        User user=(User) session.getAttribute("user");
        DiscussService service=new DiscussService();

        try {
            List<Discuss> mapList=service.FindAllDiscuss();
            session.setAttribute("mapList",mapList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (request.getSession().getAttribute("admin_0").equals("admin")){
            request.getRequestDispatcher("admin/share.jsp").forward(request,response);
        }
        request.getRequestDispatcher("/share.jsp").forward(request,response);


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
