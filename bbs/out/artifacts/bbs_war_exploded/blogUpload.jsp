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
<h1>上传博客</h1>
<%
//获取用户id，判断本次上传头像是增加还是修改
//上传头像到/img文件夹下
request.setCharacterEncoding("utf-8");

boolean logState = ((String) session.getAttribute("state")).equals("on");
if (!logState) {
    String site = new String("sign_in.jsp");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", site);
}

if (request.getMethod().equalsIgnoreCase("post")) {
    String title= (String) request.getParameter("article-title-input");
    String contents = (String)request.getParameter("editordata");
    // id 从session获取


    int writerid = Integer.parseInt((String)session.getAttribute("userID"));
    int type = Integer.valueOf((String)request.getParameter("article-type-choose"));
    String p = "ourweb/blog/";
    System.out.println("路径"+p);


//	FileItem fi = (FileItem) items.get(0);
//	DiskFileItem dfi = (DiskFileItem) fi;
//	String filename = FilenameUtils.getName(dfi.getName());
//	String comment = fi.getString("utf-8");//评论内容

//	String filepath= application.getRealPath("/blog") //文件夹地址
//			+ System.getProperty("file.separator");

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
    int msg = operation.insertBlog(title, contents, writerid, type, p);

    if(msg >= 0){
        out.print("<p>保存成功！</p>");
        String newArticleID = Integer.toString(msg);
        String site = new String("article.jsp?articleID=" + newArticleID);
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
    else{
        out.print("<p>保存失败</p>");
        String site = new String("write_article.jsp?postResult=fail");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }

}


//} //if


%>
</body>
</html>