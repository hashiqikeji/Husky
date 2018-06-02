<%--
  Created by IntelliJ IDEA.
  User: 13428179979
  Date: 2018/4/16
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>我的页面</title>
</head>
<body>

    <img src="img/头部.jpg" width="1500" height="300">
    <ul class="nav nav-pills">
        <li class="active"><a href="default.jsp">主页</a></li>
        <li><a href="#"></a></li>
    </ul>

    <form role="form" id="disform" action="${pageContext.request.contextPath}/discuss" method="post">
        <div class="form-group">
            <label for="article">文本输入</label>
            <textarea class="form-control" rows="3" id="article" name="article"></textarea>
        </div>
        <div class="form-group">
            <label class="sr-only" for="title">标题</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="请输入标题">
        </div>
        <div class="form-group">
            <label class="sr-only" for="inputfile">文件输入</label>
            <input type="file" id="inputfile" name="inputfile">
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> 请打勾
            </label>
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>
</body>
</html>
