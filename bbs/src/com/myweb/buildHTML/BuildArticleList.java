package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;

public class BuildArticleList {

    public static String buildArticleLine(int id, String title, String author, String postTime, boolean isSelf) {
        String info = "<a href=\"article.jsp?articleID=" + id +"\" class=\"list-group-item list-group-item-action\">\n" +
                "                        <div class=\"row\">\r\n";
        if (title.length() > 10) {
            title = title.substring(0, 10) + "...";
        }

        if (isSelf) {
            info += "<div class=\"col-sm-7\">" + title + "</div>\r\n";
            info += "<div class=\"col-sm-2\">" + author + "</div>\r\n";
            info += "<div class=\"col-sm-2\">" + postTime + "</div>\r\n";
            info += "<div class=\"col-sm-1\"><object><a href=\"deleteArticle.jsp?articleID="+ id +"\" role=\"button\" class=\"btn btn-sm btn-outline-warning\">É¾³ý</a></object></div>\r\n";
        } else {
            info += "<div class=\"col-sm-6\">" + title + "</div>\r\n";
            info += "<div class=\"col-sm-3\">" + author + "</div>\r\n";
            info += "<div class=\"col-sm-3\">" + postTime + "</div>\r\n";
        }
        info += "</div>\n" +
                "                    </a>";

        return info;

    }

    public static String buildArticleList(blogInfoList articleList, boolean isSelf) {
        String info = "<div class=\"list-group bbs-block-list\">\r\n";
        int count = articleList.info.size();
        for (int i = 0; i < count; i++) {
            String title = articleList.info.get(i).title;
            String author = articleList.info.get(i).writer;
            String postTime = articleList.info.get(i).public_time.substring(0, 10);
            int articleID = articleList.info.get(i).id;

            info += buildArticleLine(articleID, title, author, postTime, isSelf);
        }
        info += "</div>\n";

        return info;
    }
}
