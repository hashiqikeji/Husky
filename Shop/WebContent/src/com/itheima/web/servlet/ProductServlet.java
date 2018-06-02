package com.itheima.web.servlet;

import com.google.gson.Gson;
import com.itheima.domain.*;
import com.itheima.service.ProductService;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.JedisPoolUtils;
import com.itheima.utils.PaymentUtil;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ProductServlet")
public class ProductServlet extends BaseServlet {



    //获得我的订单
    public void myOrders(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user==null){
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

        ProductService service = new ProductService();
        //查询该用户的所有的订单信息(单表查询orders表)
        //集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据
        List<Order> orderList = service.findAllOrders(user.getUid());
        //循环所有的订单 为每个订单填充订单项集合信息
        if(orderList!=null){
            for(Order order : orderList){
                //获得每一个订单的oid
                String oid = order.getOid();
                //查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
                List<Map<String, Object>> mapList = service.findAllOrderItemByOid(oid);
                //将mapList转换成List<OrderItem> orderItems
                for(Map<String,Object> map : mapList){

                    try {
                        //从map中取出count subtotal 封装到OrderItem中
                        OrderItem item = new OrderItem();
                        //item.setCount(Integer.parseInt(map.get("count").toString()));
                        BeanUtils.populate(item, map);
                        //从map中取出pimage pname shop_price 封装到Product中
                        Product product = new Product();
                        BeanUtils.populate(product, map);
                        //将product封装到OrderItem
                        item.setProduct(product);
                        //将orderitem封装到order中的orderItemList中
                        order.getOrderItems().add(item);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

            }
        }



        //orderList封装完整了
        request.setAttribute("orderList", orderList);

        request.getRequestDispatcher("/order_list.jsp").forward(request, response);

    }



    //确认订单----更新收货人信息+在线支付
    public void confirmOrder(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        //更新收货人信息
        Map<String,String[]> properties= request.getParameterMap();
        Order order=new Order();
        try {
            BeanUtils.populate(order,properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        ProductService service=new ProductService();
        service.updateOrderAdrr(order);

        //在线支付
        //获得选择的银行
        /*if(pd_FrpId.equals("ABC-NET-B2C")){
			//介入农行的接口
		}else if(pd_FrpId.equals("ICBC-NET-B2C")){
			//接入工行的接口
		}*/
        //.......

        //只接入一个接口，这个接口已经集成所有的银行接口了  ，这个接口是第三方支付平台提供的
        //接入的是易宝支付
        // 获得 支付必须基本数据
        String orderid = request.getParameter("oid");
        String money = "0.01";
        String pd_FrpId=request.getParameter("pd_FrpId");


        // 发给支付公司需要哪些数据
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = orderid;
        String p3_Amt = money;
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
                "&p0_Cmd="+p0_Cmd+
                "&p1_MerId="+p1_MerId+
                "&p2_Order="+p2_Order+
                "&p3_Amt="+p3_Amt+
                "&p4_Cur="+p4_Cur+
                "&p5_Pid="+p5_Pid+
                "&p6_Pcat="+p6_Pcat+
                "&p7_Pdesc="+p7_Pdesc+
                "&p8_Url="+p8_Url+
                "&p9_SAF="+p9_SAF+
                "&pa_MP="+pa_MP+
                "&pr_NeedResponse="+pr_NeedResponse+
                "&hmac="+hmac;

        //重定向到第三方支付平台
        response.sendRedirect(url);

    }


    //提交订单
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session=request.getSession();

        //判断用户是否已经登录，为登录下面代码不执行
        User user=(User) session.getAttribute("user");
        if (user==null){
            //没有登录
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }

        //目的：封装好一个Order对象传递给service层
        Order order=new Order();


//       1. private String oid;//该订单的订单号
        String oid= CommonsUtils.getUUID();
        order.setOid(oid);

//       2. private Date ordertime;//下单时间
        order.setOrdertime(new Date());

//      3.  private double total;//该订单的总金额
        //获得session中的购物车
        Cart cart=(Cart) session.getAttribute("cart");
        double total=cart.getTotal();
        order.setTotal(total);


//      4.  private int state;//订单支付状态 1代表已付款 0代表未付款
        order.setState(0);

//      5.   private String address;//收货地址
        order.setAddress(null);

//      6.  private String name;//收货人
        order.setName(null);
//      7.  private String telephone;//收货人电话
        order.setTelephone(null);
//      8.  private User user;//该订单属于哪个用户
        order.setUser(user);
        //   该订单中有多少订单项 List<OrderItem> orderItems=new ArrayList<OrderItem>();
        //获得购物车中的购物项的集合map
        Map<String,CartItem> cartItems=cart.getCartItems();
        for (Map.Entry<String,CartItem> entry: cartItems.entrySet()){
            //取出每一个购物项
            CartItem cartItem=entry.getValue();
            //创建新的订单项
            OrderItem orderItem=new OrderItem();

         //   private String itemid;//订单项的id
            orderItem.setItemid(CommonsUtils.getUUID());
         //   private int count;//订单项内商品的购买数量
            orderItem.setCount(cartItem.getBuyNum());
         //   private double subtotal;//订单项小计
            orderItem.setSubtotal(cartItem.getSubtotal());
         //   private Product product;//订单项内部的商品
            orderItem.setProduct(cartItem.getProduct());
         //   private Order order;//该订单项属于哪个订单
            orderItem.setOrder(order);

            //将该订单项添加到订单的订单项集合中
            order.getOrderItems().add(orderItem);
        }

        //order对象封装完毕
        //数据传递到service层
        ProductService service=new ProductService();
        service.submitOrder(order);

        session.setAttribute("order",order);

        //页面跳转
        response.sendRedirect(request.getContextPath()+"/order_info.jsp");

    }


    //清空购物车
    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        HttpSession session=request.getSession();
        session.removeAttribute("cart");

        //跳转回cart.jsp
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }



    //删除购物车中某单一商品
    public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得要删除的item的pid
        String pid=request.getParameter("pid");
        //删除session中的购物车中的购物项集合中的item
        HttpSession session=request.getSession();
        Cart cart=(Cart) session.getAttribute("cart");
        if (cart!=null){
            Map<String,CartItem> cartItems=cart.getCartItems();
            //需要修改的总价
            cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());

            cartItems.remove(pid);
            cart.setCartItems(cartItems);
        }

        session.setAttribute("cart",cart);

        //跳转cart.jsp
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }



    //将商品添加到购物车
    public  void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();

        ProductService service=new ProductService();

        //获得要放到购物车的商品的pid
        String pid=request.getParameter("pid");
        //获得该商品的购买数量
        int buyNum=Integer.parseInt(request.getParameter("buyNum"));

        //获得product对象
        Product product=service.findProductByPid(pid);
        //计算小计
        double subtotal=product.getShop_price()*buyNum;
        //封装CartItem
        CartItem item=new CartItem();
        item.setProduct(product);
        item.setBuyNum(buyNum);
        item.setSubtotal(subtotal);

        //获得购物车----判断是否在session中存在购物车
        Cart cart=(Cart) session.getAttribute("cart");
        if (cart==null){
            cart=new Cart();
        }

        //将购物项加入车中----key是pid
        //判断购物车中是否包含次购物项 ----判断key是否已经存在
        //如果购物车中已经存在该商品------将现在买的数量进行相加操作
        Map<String,CartItem> cartItems=cart.getCartItems();
        double newsubtotal=0.0;

        if (cartItems.containsKey(pid)){
            //取出原有商品的数量
            CartItem cartItem=cartItems.get(pid);
            int oldBuyNum=cartItem.getBuyNum();
            oldBuyNum+=buyNum;
            cartItem.setBuyNum(oldBuyNum);

            cart.setCartItems(cartItems);

            //修改小计
            //原来该商品的小计
            double oldsubtotal=cartItem.getSubtotal();
            //新买的商品的小计
            newsubtotal=buyNum*product.getShop_price();
            cartItem.setSubtotal(oldsubtotal+newsubtotal);

        }else {
            //如果车中没有该商品

            cart.getCartItems().put(product.getPid(),item);
            newsubtotal=buyNum*product.getShop_price();
        }


        //计算总计
        double total=cart.getTotal()+subtotal;
        cart.setTotal(total);


        //将车再次访问session
        session.setAttribute("cart",cart);

        //直接跳转到购物车页面
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }


    //显示商品的类别的功能
    public void varietyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService service=new ProductService();

        //先从缓存中查询categoryList，如果有就直接使用，没有再从数据库中查询
        //获得jedis对象，连接Redis数据库
        Jedis jedis= JedisPoolUtils.getJedis();
        String varietyListJson=jedis.get("varietyListJson");
        //判断varietyListJson是否为空
        if(varietyListJson==null){
            System.out.println("缓存没有数据，从数据库查询");
            //准备分类数据
            List<Variety> varietyList=service.findAllVariety();
            Gson gson=new Gson();
            varietyListJson=gson.toJson(varietyList);
            jedis.set("varietyListJson",varietyListJson);
        }

        System.out.println(varietyListJson);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(varietyListJson);
    }



    //显示首页的功能

    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService service=new ProductService();

        //准备热门商品---List<Product>
        List<Product> hotProductList= service.findHotProductList();

        //获得最新商品
        List<Product> newProductList= service.findNewProductList();

        //准备分类数据
        // List<Variety> categoryList=service.findAllCategory();

       // System.out.println(categoryList);
        // request.setAttribute("categoryList",categoryList);
        request.setAttribute("hotProductList",hotProductList);
        request.setAttribute("newProductList",newProductList);

        request.getSession().setAttribute("admin_0","null");


        //请求转发
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }


    //查找全部商品
    public void findAllProduct(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        ProductService service=new ProductService();
        List<Product> allProductList=service.findAllProduct();
        request.setAttribute("allProductList",allProductList);
        request.getRequestDispatcher("admin/product/list.jsp").forward(request,response);
    }




    //显示商品的详细信息

    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获得当前页面
        String currentPage=request.getParameter("currentPage");
        //获得商品类别
        String vid=request.getParameter("vid");

        //获得要查询的商品的pid
        String pid=request.getParameter("pid");

        ProductService service=new ProductService();
        Product product=service.findProductByPid(pid);


        request.setAttribute("product",product);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("vid",vid);

        //获得客户端携带的cookie--获得名字是pids的cookie
        String pids=pid;
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("pids".equals(cookie.getName())){
                    pids=cookie.getValue();
                    //1-3-2  本次访问商品的pid是8--------->8-1-3-2
                    //1-3-2  本次访问商品的pid是3--------->3-1-2
                    //1-3-2  本次访问商品的pid是2--------->2-1-3
                    //将pids拆成一个数组
                    String[] split= pids.split("-");//{3,1,2}
                    List<String> asList= Arrays.asList(split);//[3,1,2]
                    LinkedList<String> list=new LinkedList<String>(asList);//[3,1,2]
                    //判断集合中是否存在当前的pid
                    if (list.contains(pid)){
                        //包含当前查看商品的pid
                        list.remove(pid);
                        list.addFirst(pid);
                    }else {
                        //不包含当前查看商品的pid，直接将该pid放到头上
                        list.addFirst(pid);
                    }
                    //将[3,1,2]转换成3-1-2字符串
                    StringBuffer sb=new StringBuffer();
                    for (int i=0;i<list.size()&&i<7;i++){
                        sb.append(list.get(i));
                        sb.append("-");//3-1-2
                    }
                    //去掉3-1-2-后的-
                    pids=sb.substring(0,sb.length()-1);
                }
            }
        }
        Cookie cookie_pids=new Cookie("pids",pids);
        response.addCookie(cookie_pids);

        //请求转发
        request.getRequestDispatcher("/product_info.jsp").forward(request,response);
    }


    //根据商品的类别获得商品的列表

    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获得vid
        String vid=request.getParameter("vid");

        String currentPageStr=request.getParameter("currentPage");
        if (currentPageStr==null) currentPageStr="1";
        int currentPage=Integer.parseInt(currentPageStr);
        int currentCount=12;

        ProductService service=new ProductService();
        PageBean pageBean=service.findProductListByVid(vid,currentPage,currentCount);

        request.setAttribute("pageBean",pageBean);
        request.setAttribute("vid",vid);


        //定义一个记录历史商品的集合
        List<Product> historyProductList=new ArrayList<Product>();

        //获得客户端携带名字叫pids的cookie
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("pids".equals(cookie.getName())){
                    String pids=cookie.getValue();//3-2-1
                    String[] split=pids.split("-");
                    for (String pid:split){
                        Product pro=service.findProductByPid(pid);
                        historyProductList.add(pro);
                    }
                }
            }
        }

        //将历史记录得集合放到request作用域中
        request.setAttribute("historyProductList",historyProductList);
        //请求转发
        request.getRequestDispatcher("/product_list.jsp").forward(request,response);

    }


    //删除数据库中某一商品
    public void delProduct(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        ProductService service=new ProductService();
        String pid=request.getParameter("pid");
        service.delProduct(pid);
        findAllProduct(request,response);
    }

    //删除数据库中某一商品类别
    public void delVariety(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        ProductService service=new ProductService();
        String vid=request.getParameter("vid");
        service.delVariety(vid);
        findVariety(request,response);
    }


    //查找商品类别
    public  void findVariety(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        ProductService service=new ProductService();
        List<Variety> varietyList=service.findAllVariety();
        request.setAttribute("varietyList",varietyList);
        request.getRequestDispatcher("admin/category/list.jsp").forward(request,response);
    }


}
