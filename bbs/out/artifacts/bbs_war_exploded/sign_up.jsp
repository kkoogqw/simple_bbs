<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/20
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>


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

    // 注册前应该清空session中的登陆状态
    session.removeAttribute("userID");
    session.setAttribute("state", "off");

    boolean type = false; // 表示这里是注册页面

    boolean hasSubmittd = false;
    String regUserName = "";
    String regPassword = "";
    String regReinputPassword = "";
    int serverCode = -1;

    if (request.getMethod().equalsIgnoreCase("post")) {
        regUserName = request.getParameter("reg-name");
        regPassword = request.getParameter("reg-pwd");
        regReinputPassword = request.getParameter("reg-re-pwd");
        // 服务器进行注册 返回代码
        serverCode = operation.insertUser(regUserName, regPassword, 0);
        if (!regPassword.equals(regReinputPassword)) {
            serverCode = 3;
        }
        if (regUserName ==null || regUserName == "" || regPassword == null ||regPassword == "") {
            serverCode = 4;
        }
    }
    hasSubmittd = (serverCode != -1) ;


%>

<!DOCTYPE html>
<html>

<head>
    <title>注册</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">

    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="style/sign_up.css" type="text/css">
</head>

<body>

<%--构建顶部导航栏--%>
<%
    String s_key = (String) request.getParameter("getKey");
    out.write(BuildTopBar.buildTopBar(siteName, fistLink, linkAddress, s_key));
%>
<%--顶部导航部分结束--%>

<div class="container">
    <div id="signup-part">
        <h2>用户注册</h2>

        <!-- 下面两部分是对登陆状态的提示 分别为注册成功 / 注册失败 -->

        <%
            if (!hasSubmittd) {
                out.write("");
            }else {
                if (serverCode != 0) {
                    out.write(BuildSignInfo.buildFailInfo(type, serverCode));
                    hasSubmittd = false;
                } else {
                    out.write(BuildSignInfo.buildSuccessInfo(type));
                    hasSubmittd = false;
                    // 注册成功后更改session
                    // 从数据库获取注册成功后生成的ID
                    // todo:
                    userInfo regUser = operation.userQueryByName(regUserName);
                    String regID = Integer.toString(regUser.id);

                    session.setAttribute("userID", regID);
                    session.setAttribute("state", "on");

                    // 注册成功后重定向到主页
                    // 延时 1s
                    Robot r = new Robot();
                    r.delay(500);
                    String site = new String("home.jsp");
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", site);
                }
            }
        %>
        <%--<%="code="+serverCode%>--%>

        <!--  -->

        <div id="sign_up_table" class="tab-content">
            <form action="sign_up.jsp" method="post">
                <input type="hidden" name="user" id="user">
                <div class="form-group">
                    <label for="user-name-input">用户名</label>
                    <input name="reg-name" type="text" class="form-control" id="user-name-input" placeholder="Enter user name">
                </div>
                <div class="form-group">
                    <label for="user-pwd-input">密码</label>
                    <input name="reg-pwd" type="password" class="form-control" id="user-pwd-input" placeholder="Enter password">
                </div>
                <div class="form-group">
                    <label for="user-repwd-input">再次输入密码</label>
                    <input name="reg-re-pwd" type="password" class="form-control" id="user-repwd-input" placeholder="Enter password again">
                </div>
                <br>
                <button type="submit" class="btn btn-primary btn-block">注册</button>
                <button type="button" class="btn btn-block" onclick="location.href='sign_in.jsp'">登录</button>
            </form>
        </div>
        <br>
    </div>
</div>


</body>

</html>
