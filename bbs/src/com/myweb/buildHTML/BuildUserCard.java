package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;

public class BuildUserCard {

    public static String buildUserImage(String imageAddress) {

        String info = "<img class=\"card-img-top\" src=\"";
        info += imageAddress;
        info += "\" alt=\"Card image\"\n" +
                "                 style=\"width:100%\">";
        return info;

    }

    public static String buildUserBasicInfo(String userName, String assignment, String userHomeLink, boolean logState) {
        userInfo user = operation.userQueryByName(userName);
        String s_id = Integer.toString(user.id);
        String info = "<div class=\"card-body\">";
        String now = "";
        String btnLink = logState ? "log_out.jsp" : "sign_in.jsp";
        info += "<h5 class=\"card-title\">" + userName + "</h5>\r\n";
        info += "<p class=\"card-text\">"+ assignment +"</p>\r\n";
        info += "<a href=\"" + userHomeLink + "?readInfo=self&readInfoID=" + s_id + "\" class=\"btn btn-primary btn-sm";
        if (logState) {
            info += "";
            now = "退出";
        } else {
            info += " disabled";
            now = "登录";
        }
        info += "\">我的主页</a>\n" +
                "<a href=\"" + btnLink +"\" class=\"btn btn-sm\">" + now +"</a>" +
                "            </div>\r\n";
        return info;
    }

    public static String buildUserMenu(boolean logState) {
        String info = "<div class=\"list-group\" id=\"user-menu\">\r\n";
        String myArticleLink = "";
        String myCommentLink = "";
        String myMessageLink = "";
        String myFoucsLink = "";
        String myStarLink = "";
        if (!logState) {
            myArticleLink = "<a href=\"#\" class=\"list-group-item list-group-item-action disabled\">我的文章</a>\r\n";
            myCommentLink = "<a href=\"#\" class=\"list-group-item list-group-item-action disabled\">我的讨论</a>\r\n";
            myFoucsLink = "<a href=\"#\" class=\"list-group-item list-group-item-action disabled\">我的消息</a>\r\n";
            myMessageLink = "<a href=\"#\" class=\"list-group-item list-group-item-action disabled\">我的关注</a>\r\n";
            myStarLink = "<a href=\"#\" class=\"list-group-item list-group-item-action disabled\">我的收藏</a>\r\n";
        } else {
            myArticleLink = "<a href=\"show_list.jsp?getList=article&getMethod=self\" class=\"list-group-item list-group-item-action\">我的文章</a>\r\n";
            myCommentLink = "<a href=\"show_list.jsp?getList=comment&getMethod=self\" class=\"list-group-item list-group-item-action\">我的讨论</a>\r\n";
            myFoucsLink = "<a href=\"show_list.jsp?getList=user&getMethod=self\" class=\"list-group-item list-group-item-action\">我的关注</a>\r\n";
            myMessageLink = "<a href=\"show_list.jsp?getList=msg&getMethod=self\" class=\"list-group-item list-group-item-action\">我的消息</a>\r\n";
            myStarLink = "<a href=\"show_list.jsp?getList=star&getMethod=self\" class=\"list-group-item list-group-item-action\">我的收藏</a>\r\n";
        }
        info += myArticleLink + myCommentLink + myFoucsLink + myMessageLink + myStarLink;
        info += "</div>\r\n";

        return info;
    }

    public static String buildUserCard(String img, String uname, String assign, String uLink, boolean logState) {
        String info = "<div class=\"card\" id=\"user-card\">\r\n";
        info += buildUserImage(img);
        info += buildUserBasicInfo(uname, assign, uLink, logState);
        info += "</div>\r\n";
        info += buildUserMenu(logState);

        return info;
    }

}
