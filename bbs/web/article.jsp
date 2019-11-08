<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/21
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.sqlop.operation.*" %>
<%@ page import="java.sql.BatchUpdateException" %>

<%request.setCharacterEncoding("utf-8");%>

<%
    String siteName = "Campus BBS";
    String fistLink = "中山大学";
    String linkAddress = "http://www.sysu.edu.cn/2012/cn/index.htm";



    String imgURL = null;
    String name = null;
    String assign = null;
    boolean logState = false;
    String sessionUserID = (String) session.getAttribute("userID");
    String sessionState = (String) session.getAttribute("state");
    if (sessionState == null || sessionState == "off") {
        // 未登陆状态
        logState = false;
        // 将一些变量设置为未登录状态
        imgURL = "https://static.runoob.com/images/mix/img_avatar.png";
        name = "未登录";
        assign = "请登录查看!";

        String site = new String("sign_in.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);


    } else {
        // 一登陆状态
        logState = true;
        // 从数据库获取相关信息
        userInfo logUser = operation.userQueryById(Integer.parseInt(sessionUserID));
        imgURL = logUser.head_img;
        name = logUser.nickname;
        assign = logUser.sign;
    }


    String myLink = "user_info.jsp";
    String text = "";
    // 获取url
    String commentReturn = request.getParameter("commentMsg");
    if (commentReturn == null) {
        out.write("");
    } else if (commentReturn.equals("0")) {
        out.write("<script>alert(\"评论成功!\")</script>");
    } else {
        out.write("<script>alert(\"评论失败! 请重试\")</script>");
    }
    int i_articleID = 0;

    String articleID = (String) request.getParameter("articleID");
    if (articleID==null || articleID.equals("0") || Integer.parseInt(articleID) <= 0) {
        String site = new String("home.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    } else {
        i_articleID = Integer.parseInt(articleID);
    }

%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">

    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="style/home.css" type="text/css">

    <link href="editor/summernote-bs4.css" rel="stylesheet">
    <script src="editor/summernote-bs4.js"></script>

    <title>论坛首页</title>
</head>

<body>
<%--生成顶栏--%>
<%
    String s_key = (String) request.getParameter("getKey");
    out.write(BuildTopBar.buildTopBar(siteName, fistLink, linkAddress, s_key));
%>
<%----%>

<div class="container">
    <div id="side-bar">
        <%--        侧边栏用户信息显示 --%>
        <%
            String temp = BuildUserCard.buildUserCard(imgURL, name, assign, myLink, logState);
            out.write(temp);
        %>
    </div>
    <div id="main-block">
        <div class="row">
<%--            使用绘制方法--%>
<%--            绘制对应的文章和相关评论--%>
            <%
                out.write(BuildArticlePage.buildArticleSection(i_articleID, Integer.parseInt(sessionUserID)));

                out.write(BuildArticlePage.buildAllComment(Integer.parseInt(sessionUserID), i_articleID));
//              绘制评论编辑器
                out.write("<br>");
                out.write(BuildEditor.buildCommentEditor(i_articleID));
            %>
<%--            --%>
        </div>

    </div>
</div>

</body>

</html>
