<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/21
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.sqlop.operation.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@page import="java.awt.Robot" %>

<%request.setCharacterEncoding("utf-8");%>

<%
    // title
    String siteName = "Campus BBS";
    String fistLink = "中山大学";
    String linkAddress = "http://www.sysu.edu.cn/2012/cn/index.htm";

    boolean isLogged = false;
    String logState = (String) session.getAttribute("state");
    if (logState == "on") {
        isLogged = true;
    } else {
        String site = new String("sign_in.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
    String nowUserID = (String) session.getAttribute("userID");
    System.out.println("vvv+"+ nowUserID);
    int i_nowUserID = Integer.parseInt(nowUserID);

    String readInfo = (String)request.getParameter("readInfo");
    String readInfoID = (String)request.getParameter("readInfoID");

    System.out.println(readInfo);
    System.out.println(readInfoID);

    // 修改部分
    String submitResult = (String) request.getParameter("result");
    if (submitResult == null) {
        out.write("");
    } else if (submitResult.equals("0")) {
        out.write("<script>alert(\"修改成功!\")</script>");

    } else {
        out.write("<script>alert(\"修改失败!\")</script>");
    }


%>

<!DOCTYPE html>
<html>

<head>
    <title>用户信息</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">

    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="style/home.css" type="text/css">
</head>

<body>

<%--绘制顶部栏--%>
<%
    String s_key = (String) request.getParameter("getKey");
    out.write(BuildTopBar.buildTopBar(siteName, fistLink, linkAddress, s_key));
%>

<div class="container">
    <%
        int v_id = Integer.parseInt(readInfoID);
        if (isLogged) {
            if (readInfo.equals("other") ) {
                if (i_nowUserID == v_id) {
                    out.write(BuildUserInfoPage.buildSelfInfoPage(v_id));
                } else {
                    System.out.println("other");
                    out.write(BuildUserInfoPage.buildVisitorInfo(v_id));
                }

            }
            else if (readInfo.equals("self")){
                System.out.println("ok");
                out.write(BuildUserInfoPage.buildSelfInfoPage(i_nowUserID));
            }
        } else {
            String site = new String("sign_in.jsp");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        }

    %>
</div>


</body>

</html>