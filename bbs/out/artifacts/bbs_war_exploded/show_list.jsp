<%--
  Created by IntelliJ IDEA.
  User: kkoog
  Date: 2019/6/21
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.myweb.buildHTML.*" %>
<%@page import="com.myweb.sqlop.*" %>
<%@page import="com.myweb.sqlop.operation.*" %>
<%@ page import="javax.rmi.ssl.SslRMIClientSocketFactory" %>

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
        // 定位到指定页面
        String site = new String("sign_in.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);

    } else {
        // 一登陆状态
        logState = true;
        // 从数据库获取相关信息
        userInfo logUser = operation.userQueryById(Integer.valueOf(sessionUserID).intValue());
        imgURL = logUser.head_img;
        name = logUser.nickname;
        assign = logUser.sign;
    }

    // 现在使用session 传递,不用url
    String myLink = "user_info.jsp";

    // 通过url获取参数 决定显示什么东西
    // 从session获取的
    String nowUserID = (String) session.getAttribute("userID");
    // 状态已经得到

    // 从URL中获取
    String getList = request.getParameter("getList"); // artile -> 获取文章 | comment -> 获取评论 | user -> 获取用户列表 | focus -> 文章收藏
    String getMethod = request.getParameter("getMethod"); // self -> 查看自己 | more -> home入口 查看一个专题的全部 | search -> 从搜索查看
    String getType = request.getParameter("getType"); // 1/2/3/4 -> 在通过专题more对应的专题编号 | title/author -> 搜索关键字
    String getKey = request.getParameter("getKey"); // 搜索时才使用, 搜索关键字

    // 检查获取结果
    if ((getList == null || getList.equals("")) || (getMethod == null || getMethod.equals(""))) {
        getList = (String) session.getAttribute("getList");
        getMethod = (String) session.getAttribute("getMethod");
        getType = (String) session.getAttribute("getType");
        getKey = (String) session.getAttribute("getKey");
    } else {
        session.setAttribute("getList", getList);
        session.setAttribute("getMethod", getMethod);
        session.setAttribute("getType", getType);
        session.setAttribute("getKey", getKey);
    }


    blogInfoList articleList = new operation.blogInfoList();
    commentInfoList commentList = new commentInfoList();
    userInfoList userList = new userInfoList();

    // 页码设计
    int totlePage = 0;
    int linePerPage = 15;
    int nowPage = 1;
    String getPage = (String) request.getParameter("getPage");
    if (getPage==null || getPage.equals("")) {
        nowPage = 1;
    } else {
        nowPage = Integer.parseInt(getPage);
    }


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

    <title>列表显示</title>
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
<%--            构建列表--%>
            <%
                if (getList == null || getMethod == null) {
                    // 没有url传入 -> 定位主页
                    String site = new String("home.jsp");
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", site);
                }

                //  todo 查看自己的文章
                else if (getMethod.equals("self") && getList.equals("article")) {
                    articleList = operation.blogQueryByWriterId(Integer.parseInt(nowUserID));
                    int recordCount = articleList.length;
                    if (recordCount != 0) {
                        totlePage = (recordCount%linePerPage==0) ? recordCount/linePerPage : (recordCount/linePerPage)+1;
                    }
                    blogInfoList show = operation.getBlogByPage(articleList, nowPage, linePerPage);
                    String title = "我发布的文章";
                    String intro = "我一共发布了" + articleList.length + "篇文章";
                    out.write(BuildBlock.buildArticleBlock_list(show, title, intro, nowPage, totlePage, true));

                }

                // todo 查看自己的评论内容列表
                else if (getMethod.equals("self") && getList.equals("comment")) {
                    // todo:
                    commentList = operation.commentQueryByUserId(Integer.parseInt(nowUserID));
                    String title = "我的评论内容";
                    String intro = "我一共提交了" + commentList.length + "条评论";
                    out.write(BuildBlock.buildCommentBlock(commentList, title, intro, 1, commentList.length, Integer.parseInt(nowUserID)));
                }

                // todo 查看自己的朋友(关注)
                else if (getMethod.equals("self") && getList.equals("user")) {
                    // 获取关注列表
                    userList = operation.friendQuery(Integer.parseInt(nowUserID));
                    String title = "我关注的人";
                    String intro = "我一共关注了" + userList.length + "个朋友";
                    out.write(BuildBlock.buildUserBlock(userList, title, intro, 1, userList.length, Integer.parseInt(nowUserID)));
                }

                else if (getMethod.equals("self") && getList.equals("star")) {
                    articleList = operation.collectQuery(Integer.parseInt(nowUserID));

                    int recordCount = articleList.length;
                    if (recordCount != 0) {
                        totlePage = (recordCount%linePerPage==0) ? recordCount/linePerPage : (recordCount/linePerPage)+1;
                    }
                    blogInfoList show = operation.getBlogByPage(articleList, nowPage, linePerPage);
                    String title = "我收藏的文章";
                    String intro = "我一共收藏了" + articleList.length + "篇文章";
                    out.write(BuildBlock.buildArticleBlock_list(show, title, intro, nowPage, totlePage, false));

                }

                /**
                 * todo:
                 * 上面是针对个人的信息显示
                 * 下面时对页面内容的操作
                 */

                // todo 从主页跳转过来的某一类板块
                else if (getMethod.equals("more")) {
                    int i_type = Integer.parseInt((String)request.getParameter("getType"));
                    articleList = operation.getLatestBlog_all(i_type);
                    int recordCount = articleList.length;
                    if (recordCount != 0) {
                        totlePage = (recordCount%linePerPage==0) ? recordCount/linePerPage : (recordCount/linePerPage)+1;
                    }
                    blogInfoList show = operation.getBlogByPage(articleList, nowPage, linePerPage);
                    out.write(BuildBlock.buildArticleBlock_list(show, blockName[i_type], blockDescription[i_type], nowPage, totlePage, false));
                }

                // todo 搜索功能-->有待完善
                else if (getMethod.equals("search")) {
                    String search_key = (String) request.getParameter("search-input");
                    System.out.println("search-key"+search_key);
                    if (search_key==null || search_key.equals("")) {
                        out.write("<script>alert(\"搜索无效!\")</script>");
                    } else {
                        articleList = operation.blogQueryByWord(search_key);
                        int recordCount = articleList.length;
                        if (recordCount != 0) {
                            totlePage = (recordCount%linePerPage==0) ? recordCount/linePerPage : (recordCount/linePerPage)+1;
                        }
                        blogInfoList show = operation.getBlogByPage(articleList, nowPage, linePerPage);
                        System.out.println("search-re-count="+articleList.length);
                        out.write(BuildBlock.buildArticleBlock_list(articleList, "搜索结果", "一共找到" + articleList.length +"个结果", nowPage, totlePage, false));
                    }
                }
            %>
            <%--            --%>
        </div>

    </div>
</div>

</body>

</html>