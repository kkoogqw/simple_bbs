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
    <title>Uploading user info</title>
</head>
<body>
<h1>上传个人信息</h1>
<%
    //获取用户id，判断本次上传头像是增加还是修改
    request.setCharacterEncoding("utf-8");

    if (request.getMethod().equalsIgnoreCase("post")) {

        String nickname = (String)request.getParameter("username");
        String email_add = (String)request.getParameter("email");
        String phone_num = (String)request.getParameter("tel");
        String birthday = (String)request.getParameter("birth");
        String realname = (String)request.getParameter("real-name");
        String sign = (String)request.getParameter("introduce");
        String org_pwd = (String)request.getParameter("org-pwd");
        String new_pwd = (String)request.getParameter("new-pwd");
        String renew_pwd = (String)request.getParameter("re-new-pwd");
        String sex = (String)request.getParameter("sex");
        // id 从session获取
        int id = Integer.parseInt((String)request.getParameter("readInfoID"));
        String self = (String)request.getParameter("readInfo");

        int msg = -1;
        int upmsg = 0;
        if((org_pwd==null&&new_pwd==null&&renew_pwd==null)){//需要修改密码
            int pass= operation.verifyLogIn(nickname, org_pwd, false);
            if(pass!=0){
                //账号密码不正确，不通过
                String site = new String("user_info.jsp");
                site += "?readInfo=" + self + "&readInfoID=" + id + "&result=" + 1;
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", site);
            }
            else if(new_pwd.equals(renew_pwd)){
                //两次输入密码不同，不通过
                String site = new String("user_info.jsp");
                site += "?readInfo=" + self + "&readInfoID=" + id + "&result=" + 2;
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", site);
            }else {
                //修改密码
                upmsg = operation.updatePwd(id, new_pwd);
            }

        }
        //修改密码外的其他信息
        msg = operation.userUpdateInfo(id, nickname, new_pwd, email_add, phone_num, sex, birthday, sign, realname);
        String site = new String("user_info.jsp");
        if(msg==0 && upmsg==0) System.out.print("保存成功");
        else System.out.print("msg--" + msg + "upmsg" + upmsg +"保存失败");

        site += "?readInfo=" + self + "&readInfoID=" + id + "&result=" + 0;
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
%>
</body>
</html>