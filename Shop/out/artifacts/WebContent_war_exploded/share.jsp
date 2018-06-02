<%--
  Created by IntelliJ IDEA.
  User: 13428179979
  Date: 2018/4/15
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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




</head>

<body >

    <img src="img/7.gif" height="30%" width=1350>

    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="default.jsp" style="font-size: 30" ><span class="label label-default">首页</span></a>
            </div>
            <div>
                <a style="font-size: 30" href="${pageContext.request.contextPath}/user?method=is_login"><span class="label label-default">参与分享</span></a>
            </div>
        </div>
    </nav>

    <c:forEach items="${mapList}" var="discuss">
        <div id="container" class="col-md-12">
            <div id="photo" class="">
                <img src="${discuss.image}" width="80%" />
                <h4 align="left">来自用户:<span style="color: #ceaa00"><u>${discuss.username}</u></span>的分享</h4>
            </div>
            <div id="content">
                <div id="text">${discuss.title}</div>
                <div id="news">${discuss.article}</div>
            </div>

        </div>

        <img src="img/分隔条1.jpg">
    </c:forEach>


    

    <!--div id="container" >
        <div id="photo" class="">
            <img src="dog/阿拉斯加.jpg" width="80%" />
        </div>
        <div id="content">
            <div id="text">阿拉斯加</div>
            <div id="news">阿拉斯加雪橇犬是最古老的雪橇犬之一。
                它的名字来自爱斯基摩人的伊努伊特族的一个叫做马拉缪特的部落，
                这个部落生活在阿拉斯加西部一个叫做扣赞伯(Kotzebue)的岸边。
                在阿拉斯加成为美国领土的一部分之前，这一地区叫做Alashak或是Alyeska，
                翻译出来就是“广阔的土地”，这是发现这一地区的俄国人给它取的名字。
                这种犬与同在阿拉斯加的其它犬种不同，四肢强壮有力，培育它的目的是
                为了耐力而不是速度，因而它们的主要用途是拉雪橇。
            </div>
        </div>
    </div-->







</body>
</html>
