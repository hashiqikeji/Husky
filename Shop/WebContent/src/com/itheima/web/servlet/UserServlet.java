package com.itheima.web.servlet;

import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.service.ProductService;
import com.itheima.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet")
public class UserServlet extends BaseServlet {


    //用户注销
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session=request.getSession();
        //从session中将user删除
        session.removeAttribute("user");

        //删除存储在客户端的Cookie
        Cookie cookie_username = new Cookie("cookie_username","");
        cookie_username.setMaxAge(0);
        //创建存储密码的cookie
        Cookie cookie_password = new Cookie("cookie_password","");
        cookie_password.setMaxAge(0);

        response.addCookie(cookie_username);
        response.addCookie(cookie_password);

        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }


    //用户登录
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        //获得输入的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //对密码进行加密
        //password = MD5Utils.md5(password);

        //将用户名和密码传递给service层
        UserService service = new UserService();
        User user = null;
        try {
            user = service.login(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //判断用户是否登录成功 user是否是null
        if(user!=null){
            //登录成功
            //***************判断用户是否勾选了自动登录*****************
            String autoLogin = request.getParameter("autoLogin");
            if("true".equals(autoLogin)){
                //要自动登录
                //创建存储用户名的cookie
                Cookie cookie_username = new Cookie("cookie_username",user.getUsername());
                cookie_username.setMaxAge(10*60);
                //创建存储密码的cookie
                Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
                cookie_password.setMaxAge(10*60);

                response.addCookie(cookie_username);
                response.addCookie(cookie_password);

            }


            //重定向时request的作用范围内数据失效，所以把数据存储在session中
            ProductService service1=new ProductService();
            List<Product> hotProductList= service1.findHotProductList();
            List<Product>newProductList=service1.findNewProductList();
            session.setAttribute("hotProductList",hotProductList);
            session.setAttribute("newProductList",newProductList);

            //***************************************************
            //将user对象存到session中
            session.setAttribute("user", user);

            //重定向到首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{
            request.setAttribute("loginError", "用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }


    public void is_login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if (user==null){
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        else {
            request.getRequestDispatcher("text.jsp").forward(request,response);
        }
    }
}
