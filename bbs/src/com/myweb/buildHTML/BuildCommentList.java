package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;

public class BuildCommentList {

    public static String buildCommentLine(int id, String commentInfo, String commentAuthor, String commentTime, int articleID) {
        String commentSummary = "";
        if (commentInfo.length() >= 12 ) {
            commentSummary = commentInfo.substring(0, 10);
            commentSummary += "... ...";
        } else {
            commentSummary = commentInfo;
        }
        String articleTitle = operation.blogQueryById(articleID).title;

        String info = "<a href=\"article.jsp?articleID=" + id +"\" class=\"list-group-item list-group-item-action\">\n" +
                "                        <div class=\"row\">\r\n";
        info += "<div class=\"col-sm-6\">回复文章 @ \"" + articleTitle + "\" </object></div>\r\n";
        info += "<div class=\"col-sm-2\">" + commentAuthor + "</div>\r\n";
        info += "<div class=\"col-sm-2\">" + commentTime + "</div>\r\n";
        info += "<div class=\"col-sm-2\"><object><a href=\"deleteComment.jsp?commentID=" + id + "\" role=\"button\" class=\"btn btn-sm btn-outline-warning\">删除</a></object></div>\r\n";

        info += "</div>\n" +
                "                    </a>";

        return info;
    }

    public static String buildCommentList(commentInfoList commentList) {
        String info = "<div class=\"list-group bbs-block-list\">\r\n";
        int count = commentList.info.size();
        for (int i = 0; i < count; i++) {
            int commentID = commentList.info.get(i).id;
            // 获取i篇评论的基本内容
            String details = operation.getCommentString(commentList.info.get(i));
            // id -> name
            int author_id = commentList.info.get(i).commenterid;
            String authorName = operation.userQueryById(author_id).nickname;
            // timestamp -> stirng
            // String postTime = articleList.info.get(i).time;
            String postTime = commentList.info.get(i).publishtime.substring(0, 11);
            int articleID = commentList.info.get(i).blogid;
            info += buildCommentLine(commentID, details, authorName, postTime, articleID);
        }
        info += "</div>\n";

        return info;
    }
}

