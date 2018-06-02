<%--
  Created by IntelliJ IDEA.
  User: 13428179979
  Date: 2018/4/18
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>爱宠分享社区</title>
        <style>
            #container {width: 1500px; height: 400px}
            #photo {float: left; width: 200px;}
            #content {float: inherit;
                width: 1000px;}
        </style>


        <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>



        <script type="text/javascript">
            function delDis(did) {
                if (confirm("您是否要删除该项？")){
                    location.href="${pageContext.request.contextPath}/delDiscuss?did="+did;
                }
            }


        </script>

    </head>

    <body >

    <img src="img/7.gif" height="30%" width=1350>

    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="default.jsp" style="font-size: 30" ><span class="label label-default">首页</span></a>
            </div>
            <div>
                <a style="font-size: 30" href="text.jsp"><span class="label label-default">参与分享</span></a>
            </div>
        </div>
    </nav>

    <c:forEach items="${mapList}" var="discuss">
        <div id="container" class="col-md-12">
            <div id="photo" class="">
                <img src="${discuss.image}" width="80%" />
                <h4 align="left">来自用户:<span style="color: #ceaa00"><u>${discuss.username}</u></span>的分享</h4>

                <a href="javascript:;" onclick="delDis('${discuss.did}')" class="delete">删除</a>
            </div>
            <div id="content">
                <div id="text">${discuss.title}</div>
                <div id="news">${discuss.article}</div>
            </div>

        </div>

        <img src="img/分隔条1.jpg">
    </c:forEach>

</body>
</html>
