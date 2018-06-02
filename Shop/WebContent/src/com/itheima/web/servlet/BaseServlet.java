package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

    //在Servlet中，主要的方法是service（），当客户端请求到来时，Servlet容器将调用Servlet的此方法对请求进行处理。
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        //获得当前
        try {
            //1.获得请求的method的名称
            String methodName=req.getParameter("method");
            //2.获得当前被访问的对象的字节码对象
            Class clazz=this.getClass();//ProductServlet.class-------UserServlet.class
            //获得当前字节码对象中的指定方法
            Method method=clazz.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);

            //4.执行相应的功能方法
            method.invoke(this,req,resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
