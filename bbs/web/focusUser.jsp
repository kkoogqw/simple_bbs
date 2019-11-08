<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*,org.apache.commons.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.buildHTML.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Uploading head image</title>
</head>
<body>
<%
//添加关注
String imgURL = null;
String name = null;
String assign = null;
boolean logState = false;
String sessionUserID = (String) session.getAttribute("userID");
String sessionState = (String) session.getAttribute("state");
int uID = Integer.parseInt((String)request.getParameter("uID"));

String focusUser="";
if (sessionState == null || sessionState == "off") {
    // 未登陆状态
    logState = false;
    // 将一些变量设置为未登录状态
    imgURL = "https://static.runoob.com/images/mix/img_avatar.png";
    name = "未登录";
    assign = "请登录查看!";
	focusUser = "fail";
} else {
    // 一登陆状态
    logState = true;
    // 关注
    int msg = operation.insertFriend(Integer.parseInt(sessionUserID), uID);
    if(msg==0) focusUser = "success";
    else focusUser = "fail";
    
}
// 定位到指定页面
String site = new String("show_list.jsp?focusUser="+focusUser + "&getMethod=self&getList=user");
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site);


%>
</body>
</html>