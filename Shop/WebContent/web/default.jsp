<%--
  Created by IntelliJ IDEA.
  User: 13428179979
  Date: 2018/3/30
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        response.sendRedirect(request.getContextPath()+"/product?method=index");
    %>
</body>
</html>
