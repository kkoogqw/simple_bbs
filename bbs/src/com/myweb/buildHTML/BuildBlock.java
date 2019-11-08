package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;

public class BuildBlock {

    private static String userThemeColor = "border-color: mediumslateblue;";
    private static String commentThemeColor = "border-color: yellowgreen;";

    // 单独列表的内容
    public static String buildArticleBlock_list(blogInfoList articleList, String blockName, String blockInfo, int nowPage, int totlePages, boolean isSelf) {
        System.out.println("check-p-1");

        int articleCount = articleList.info.size();

        String blockHeight = Integer.toString(210 + 50 * articleCount) + "px";
        System.out.println("check-p-2");

        String info = "<div class=\"col-12 bbs-block-all\" style=\"height: " + blockHeight + "\">\r\n";
        info += "<h3 class=\"bbs-block-title\">" + blockName + "</h3>";
        info += "<p class=\"bbs-block-introduce\">" + blockInfo + "</p>";

        info += BuildArticleList.buildArticleList(articleList, isSelf);
        System.out.println("check-p-3");

        if (articleCount == 0) {
            return "没有数据!";
        } else {

            info += BuildPageCounter.buildPageCounter(nowPage, totlePages);
            System.out.println("check-p-4");

            info += "</div>\r\n";
            System.out.println("check-p-5");
        }

        return info;
    }

    // 主页板块的设计
    public static String buildArticleBlock_home(blogInfoList articleList, String blockName, String blockInfo, int type) {
        int articleCount = articleList.info.size();
        if (articleCount <= 5 && articleCount > 0) {
            articleCount = 3;
        }
        String blockHeight = Integer.toString(220 + 50 * articleCount) + "px";
        String info = "<div class=\"col-12 bbs-block-all\" style=\"height: " + blockHeight + "\">\r\n";
        info += "<h3 class=\"bbs-block-title\">" + blockName + "</h3>";
        String moreLink = "show_list.jsp?getMethod=more&getList=article&getType=" + (type);

        info += "<a href=\"" + moreLink + "\" class=\"btn btn-outline-light text-dark btn-sm bbs-block-more\">更多</a>\r\n";
        info += "<p class=\"bbs-block-introduce\">" + blockInfo + "</p>";

        info += BuildArticleList.buildArticleList(articleList, false);

        //不需要搭建页码

        info += "</div>\r\n";

        return info;
    }

    // 构建用户列表块
    public static String buildUserBlock(userInfoList userList, String blockName, String blockInfo, int nowPage, int linePerPage, int logID) {
        int userCount = userList.list.size();
        String blockHeight = Integer.toString(210 + 50 * userCount) + "px";
        String info = "<div class=\"col-12 bbs-block-all\" style=\"" + userThemeColor + "height: " + blockHeight + "\">\r\n";
        info += "<h3 class=\"bbs-block-title\">" + blockName + "</h3>";
        info += "<p class=\"bbs-block-introduce\">" + blockInfo + "</p>";

        info += BuildUserList.buildUserList(userList, logID);
        if (userCount == 0) {
            return "没有数据";
        } else {
            int pages = 0;
            if (userCount%linePerPage > 0) {
                pages = (userCount / linePerPage) + 1;
            } else {
                pages = userCount / linePerPage;
            }
            info += BuildPageCounter.buildPageCounter(nowPage, pages);

            info += "</div>\r\n";
        }
        return info;
    }

    // 构建评论列表块
    public static String buildCommentBlock(commentInfoList commentList, String blockName, String blockInfo, int nowPage, int linePerPage, int logID) {
        int commentCount = commentList.info.size();
        String blockHeight = Integer.toString(210 + 50 * commentCount) + "px";
        String info = "<div class=\"col-12 bbs-block-all\" style=\"" + commentThemeColor + "height: " + blockHeight + "\">\r\n";
        info += "<h3 class=\"bbs-block-title\">" + blockName + "</h3>";
        info += "<p class=\"bbs-block-introduce\">" + blockInfo + "</p>";

        info += BuildCommentList.buildCommentList(commentList);

        if (commentCount == 0) {
            return "没有数据";
        } else {
            int pages = 0;
            if (commentCount%linePerPage > 0) {
                pages = (commentCount / linePerPage) + 1;
            } else {
                pages = commentCount / linePerPage;
            }
            info += BuildPageCounter.buildPageCounter(nowPage, pages);

            info += "</div>\r\n";
        }

        return info;
    }

}
