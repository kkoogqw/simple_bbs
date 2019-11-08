<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/20
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@page import="java.awt.Robot" %>

<%request.setCharacterEncoding("utf-8");%>

<%
  // 这里是进行代码部分
  /**
   * 1. 定义需要的变量
   *
   * 2. 从session中getAttribute(), 根据获得的数据判断登录状态
   *    说明:
   *    geAttribute("visitorID"), 如果 != null 说明已经登录,可以进行相关
   *    登陆后的操作, 将这个"visitorID"就是数据库中的存储ID,获取相关信息
   *
   *    不同页面需要从中获取不同的getAttribute(""), 在每个页面会有说明
   *
   *    获取信息过程是根据不同页面的不同功能操作的
   *
   *    然后调用 com.myweb.buildHTML包中的类和方法, 根据获得的参数和数据
   *    绘制渲染页面
   *
   *    如果没有检查到相关Attribute, 就会重定向到 404 页面(有空设计)
   *
   */


    String site = new String("sign_in.jsp");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", site);
%>

<%--每个页面的基本布局--%>


