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
<h1>头像上传成功，正在跳转。。。</h1>
<%
	//获取用户id，判断本次上传头像是增加还是修改
//上传头像到/img文件夹下///
	//检查登录状态
	String state = (String) session.getAttribute("state");
	if (state==null || state=="") {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "sign_in.jsp");
	}


	request.setCharacterEncoding("utf-8");
	if (request.getMethod().equalsIgnoreCase("post")) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);//是否用multipart提交的?
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);

			String userID = (String) session.getAttribute("userID");
			int userid = Integer.parseInt(userID);
			//根据id查询是否有头像，如果

			FileItem fi = (FileItem) items.get(0);
			DiskFileItem dfi = (DiskFileItem) fi;
			String filename = FilenameUtils.getName(dfi.getName());
			System.out.println(filename.length());
			String last = filename.substring(filename.length()-4, filename.length());//后缀 四个.jpg .png
			if (!dfi.getName().trim().equals("")) {//getName()返回文件名称或空串

				String filepath=application.getRealPath("/img")
						+ System.getProperty("file.separator");
				System.out.println("filepath "+filepath);

				//判断路径是否存在
				File f_temp = new File(filepath);
				if(!f_temp.exists()) f_temp.mkdir();
				//if(!f.exists()) f.mkdir();

				String fileName= filepath
						+ userid
						+ last;

				System.out.println("fileName"+fileName);
				File f = new File(fileName);
				System.out.println("myfilename"+fileName);
				dfi.write(new File(fileName));

				//保存头像

				String savepath = "./img/"+userid+last;
				int msg = operation.updateHead(userid, savepath);


				if(msg==0){
					out.print("<p>保存成功！</p>");
					System.out.println("ok!!!");
					out.write("<script>alert(\"保存成功\");</script>");
				}
				else{
					out.print("<p>保存失败</p>");
					System.out.println("fail!!!");
					out.write("<script>alert(\"保存失败\");</script>");
				}

				String site = "user_info.jsp?readInfo=" + "self" + "&readInfoID=" + userID;
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);


			} //if
		} //if
	}



%>
</body>
</html>