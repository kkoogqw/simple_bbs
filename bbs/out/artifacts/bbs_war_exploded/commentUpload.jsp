<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*,org.apache.commons.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="java.nio.charset.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Uploading head image</title>
</head>
<body>
<h1>上传评论</h1>
<%
//获取用户id，判断本次上传头像是增加还是修改
//上传头像到/img文件夹下
request.setCharacterEncoding("utf-8");
//boolean isMultipart = ServletFileUpload.isMultipartContent(request);//是否用multipart提交的?
//if (isMultipart) {

	if (request.getMethod().equalsIgnoreCase("post")) {
		String contents = (String)request.getParameter("editordata");
		//System.out.println("666+" + (String)request.getParameter("articleID"));

		int blogid = Integer.parseInt((String)request.getParameter("articleID"));
		int commenterid = Integer.parseInt((String)session.getAttribute("userID"));
		String p = "ourweb/comment/";
		System.out.println("路径"+p);


		System.out.println("判断路径");
		//判断路径是否存在
		File f_temp = new File("ourweb/");
		if(!f_temp.exists()) f_temp.mkdir();
		File f = new File(p);
		if(!f.exists()) f.mkdir();

		String fileName;
		int frequence = 0;
		File _f;

		//保存文件
		System.out.println("运行函数");
		int msg = operation.insertComment(blogid, commenterid, contents, p);

		if(msg==0){
			out.print("<p>保存成功！</p>");
		}
		else{
			out.print("<p>保存失败</p>");
		}


		// 返回原来页面
		String site = new String("article.jsp?commentMsg=" + msg + "&articleID=" + blogid); // todo: 可能会加url
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
	}

//} //if

//String site = new String("？.jsp");
//response.setStatus(response.SC_MOVED_TEMPORARILY);
//response.setHeader("Location", site);
%>
</body>
</html>