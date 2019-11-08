<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/20
  Time: 23:31
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


    boolean type = true; // 表示这里是登录页面
    boolean hasSubmitted = false;//初次访问页面，没有提交过
    String logUserName = "";
    String logPassword = "";
    boolean isAdmin = false;
    int serverCode = -1;

    // 检查session 如果已经登录, 直接跳转到主页
    boolean isLogged = false;
    String logState = (String) session.getAttribute("state");
    if (logState != null && logState.equals("on")) {
        isLogged = true;
    }

    //如果已登录 重定向到home
    if (isLogged) {
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", "home.jsp");
    } else {
        // 没有登陆...
        if (request.getMethod().equalsIgnoreCase("post")) {

            String level = request.getParameter("log-type");
            isAdmin = level.equals("cmd");

            logUserName = request.getParameter("name-input");
            logPassword = request.getParameter("pwd-input");

            // 服务器进行登录 返回代码
            // todo:
            serverCode = operation.verifyLogIn(logUserName, logPassword, isAdmin);//返回 0成功 1没有匹配的账号或密码或错误的登录方式 2其他异常
            out.print(serverCode);
            hasSubmitted = true;//无论是否成功，已经提交过一次
        }
    }


    //hasSubmitted = (serverCode == 0) ;
%>


<!DOCTYPE html>
<html>

<head>
    <title>登录</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">

    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="style/sign_in.css" type="text/css">
</head>

<body>

<%--绘制顶部--%>
<%
    String s_key = (String) request.getParameter("getKey");
    out.write(BuildTopBar.buildTopBar(siteName, fistLink, linkAddress, s_key));
%>

<%----%>

<div class="container">
    <div id="signin-part">
        <h2>登录</h2>

        <ul class="nav nav-tabs" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#user-tab">用户登录</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#cmd-tab">管理员登录</a>
            </li>
        </ul>

        <!-- 下面两部分是对登陆状态的提示 分别为登陆成功 / 登录失败 -->

        <%
            if (!hasSubmitted) {//没有提交
                out.write("");
            }else {//提交
                if (serverCode != 0) {
                    out.write(BuildSignInfo.buildFailInfo(type, serverCode));
                } else {
                    out.write(BuildSignInfo.buildSuccessInfo(type));
                    // 注册成功后更改session
                    // 从数据库获取注册成功后生成的ID
                    // todo:
                    userInfo logUser = operation.userQueryByName(logUserName);
                    // 在session中写入登录用户
                    String s_logUserID = Integer.toString(logUser.id);
                    session.setAttribute("userID", s_logUserID);
                    // 在session中写入登陆状态
                    //登陆状态一共有3:
                    // 1. user 2. cmd 3. none
                    session.setAttribute("state", "on");


                    // 注册成功后重定向到主页
                    // 延时 1s
                    Robot r = new Robot();
                    r.delay(500);
                    String site = new String("home.jsp");
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", site);
                }
                hasSubmitted = false;
            }
        %>

        <!--  -->
        <!-- 选项卡形式 分为管理员 / 普通用户 -->
        <div id="sign_in_table" class="tab-content">
            <div class="tab-pane active" id="user-tab">
                <form action="sign_in.jsp" method="post">
                    <input type="hidden" name="log-type" value="user">
                    <div class="form-group">
                        <label for="user-name-input">用户名</label>
                        <input type="text" class="form-control" id="user-name-input" name="name-input" placeholder="Enter user name">
                    </div>
                    <div class="form-group">
                        <label for="user-pwd-input">密码</label>
                        <input type="password" class="form-control" id="user-pwd-input" name="pwd-input"
                               placeholder="Enter password">
                    </div>
                    <div class="form-check">
                        <div class="row">
                            <div class="col">
                                <label class="form-check-label" for="remember-pwd">
                                    <input class="form-check-input" type="checkbox" id="remember-pwd">记住密码
                                </label>
                            </div>
                            <div class="col">
                                <label for="find-pwd" class="form-check-label" style="float: right">
                                    <input type="button" value="找回密码" id="find-pwd" class="btn btn-sm"
                                           style="float: right;">
                                </label>
                            </div>
                        </div>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-primary btn-block">登录</button>
                    <button type="button" class="btn btn-block" onclick="location.href='sign_up.jsp'">注册</button>
                </form>
            </div>
            <div class="tab-pane" id="cmd-tab">
                <form action="sign_in.jsp" method="post">
                    <input type="hidden" name="log-type" value="cmd">
                    <div class="form-group">
                        <label for="cmd-id-input">管理员ID</label>
                        <input type="text" class="form-control" id="cmd-id-input" name="name-input" placeholder="Enter commader ID">
                    </div>
                    <div class="form-group">
                        <label for="cmd-pwd-input">密码</label>
                        <input type="password" class="form-control" id="cmd-pwd-input" name="pwd-input"
                               placeholder="Enter commader password">
                    </div>
                    <br>
                    <button type="submit" class="btn btn-primary btn-block" id="cmd-submit">登录</button>
                </form>
            </div>

        </div>
        <br>


    </div>
</div>


</body>

</html>