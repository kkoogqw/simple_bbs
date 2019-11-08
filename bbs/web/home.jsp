<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/20
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.sqlop.operation.*" %>

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
    } else {
        // 一登陆状态
        logState = true;
        // 从数据库获取相关信息
        userInfo logUser = operation.userQueryById(Integer.parseInt(sessionUserID));
        imgURL = logUser.head_img;
        name = logUser.nickname;
        assign = logUser.sign;
    }

    // 现在使用session 传递,不用url
    String myLink = "user_info.jsp";
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
            <%
                String[] blockName = new String[5];
                blockName[0] = "";
                blockName[1] = "好好学习 天天向上";
                blockName[2] = "吹水大厅";
                blockName[3] = "鸡你太美";
                blockName[4] = "通知公告";

                String[] blockDescription = new String[5];
                blockDescription[0] = "";
                blockDescription[1] = "大家一起建设双一流~";
                blockDescription[2] = "欢迎大家来吹水~";
                blockDescription[3] = "欢迎复习时长两天半的同学~一起来唱跳Rap篮球";
                blockDescription[4] = "并没有什么东西...";


                int blockCount = 4;
                blogInfoList articleList_1 = operation.getLatestBlog_all(1);
                blogInfoList articleList_2 = operation.getLatestBlog_all(2);
                blogInfoList articleList_3 = operation.getLatestBlog_all(3);
                blogInfoList articleList_4 = operation.getLatestBlog_all(4);

                for (int i = 1; i <= 4; i++) {
                    blogInfoList list = operation.getLatestBlog(i, 5);
                    out.write(BuildBlock.buildArticleBlock_home(list, blockName[i], blockDescription[i], i));
                }

                System.out.println(articleList_1.length);

            %>
<%--            --%>
        </div>

    </div>
</div>

</body>

</html>
