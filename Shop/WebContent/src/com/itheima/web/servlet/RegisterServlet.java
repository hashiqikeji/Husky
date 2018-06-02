package com.itheima.web.servlet;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.MailUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //获得表单数据
        Map<String,String[]> properties=request.getParameterMap();
        User user=new User();
        try {
            //自己指定一个类型转换器（将String转成Date）
            ConvertUtils.register(new Converter(){
                @Override
                public Object convert(Class clazz,Object value){
                    //将string转成date
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    Date parse=null;
                    try {
                        parse=format.parse(value.toString());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    return parse;
                }
            },Date.class);
            //映射封装
            BeanUtils.populate(user,properties);
        }catch (IllegalAccessException|InvocationTargetException e){
            e.printStackTrace();
        }

        user.setUid(CommonsUtils.getUUID());
        user.setTelephone(null);
        user.setState(0);
        String activeCode=CommonsUtils.getUUID();
        user.setCode(activeCode);

        //将user传递给service层
        UserService service=new UserService();
        boolean isRegisterSuccess=service.regist(user);

        //是否注册成功
        if (isRegisterSuccess){
            //发送激活邮件
            String emailMsg="点击下面的连接进行账户激活<a href='http://localhost:8088/active?activeCode="+activeCode+"'>"
                            +"http://localhost:8088/active?activeCode="+activeCode+"</a>";
            try {
                MailUtils.sendMail(user.getEmail(),emailMsg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            //跳到注册成功页面
            response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
        }else {
            //跳转到失败页面
            response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
