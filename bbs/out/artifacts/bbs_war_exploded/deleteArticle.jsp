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
//删除文章
String imgURL = null;
String name = null;
String assign = null;
boolean logState = false;
String sessionUserID = (String) session.getAttribute("userID");
String sessionState = (String) session.getAttribute("state");
int articleID = Integer.parseInt((String)request.getParameter("articleID"));
String deleteArticle="";
if (sessionState == null || sessionState == "off") {
    // 未登陆状态
    logState = false;
    // 将一些变量设置为未登录状态
    imgURL = "https://static.runoob.com/images/mix/img_avatar.png";
    name = "未登录";
    assign = "请登录查看!";
	deleteArticle = "fail";
} else {
    // 一登陆状态
    logState = true;
    // 删除
    int msg = operation.deleteBlog(articleID);
    if(msg==0) deleteArticle = "success";
    else deleteArticle = "fail";
    
}
// 定位到指定页面
String site = new String("show_list.jsp?deleteArticle="+ deleteArticle);
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site);


%>
</body>
</html>