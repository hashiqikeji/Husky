package com.itheima.web.servlet;

import com.itheima.service.DiscussService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DelDiscussServlet")
public class DelDiscussServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
        String did=request.getParameter("did");
       // Discuss dis=(Discuss) session.getAttribute("map");
       // List<Discuss> discussList=(List<Discuss>) session.getAttribute("map");

        DiscussService service=new DiscussService();
        try {
            service.DelDiscuss(did);
            request.getRequestDispatcher("/findAllDiscuss").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
