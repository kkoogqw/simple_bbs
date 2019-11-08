<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/21
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exit</title>
</head>
<body>
<%
    session.removeAttribute("userID");
    session.removeAttribute("state");

    // 推出后重定向到主页

    String site = new String("sign_in.jsp");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", site);
%>
已退出.
</body>
</html>
