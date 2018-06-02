package com.itheima.web.servlet;

import com.itheima.domain.Discuss;
import com.itheima.domain.User;
import com.itheima.service.DiscussService;
import com.itheima.utils.CommonsUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "DiscussServlet")
public class DiscussServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String,String[]> map=request.getParameterMap();
        Discuss discuss=new Discuss();

        String did= CommonsUtils.getUUID();
        //设置评论id以及时间
        discuss.setDid(did);
        discuss.setDistime(new Date());
        String image=(String) request.getParameter("inputfile");
        String imagesrc=request.getContextPath()+"/"+"allpet/"+image;
        discuss.setImage(imagesrc);

        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        discuss.setUid(user.getUid());

        try {
            BeanUtils.populate(discuss,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        DiscussService service=new DiscussService();
        service.inDiscuss(discuss,user.getUsername());

        session.setAttribute("map",map);
        request.getRequestDispatcher("/findAllDiscuss").forward(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
