package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;
import com.sun.xml.internal.messaging.saaj.soap.ver1_1.Body1_1Impl;

public class BuildArticlePage {

    public static String buildArticleSection(int articleID, int logUser) {
        blogInfo article = operation.blogQueryById(articleID);
        userInfo author = operation.userQueryById(article.writerid);
        String authorImgPath = author.head_img;
        String authorName = author.nickname;


        /** todo
         *
         */
        boolean isStar = false;
        isStar = operation.isCollect(logUser, articleID);

        String s ="";

        String insertText = "";
        if (isStar) {
            insertText = " disabled\">已收藏";
            s = "<a href=\"#\" class=\"list-group-item list-group-item-warning text-center btn-sm disabled" + insertText + "</a>\n";
        } else {
            insertText = "\">收藏";
            s = "<a href=\"focusArticle.jsp?articleID=" + articleID + "\" class=\"list-group-item list-group-item-warning text-center btn-sm" + insertText + "</a>\n";
        }
        String focusUSer = "";
        boolean isF = false;
        if (operation.isFriend(logUser, article.writerid)) {
            isF = true;
            focusUSer = "<a href=\"#\" class=\"btn btn-danger btn-sm\">已关注</a>" ;
        } else {
            isF = false;
            focusUSer = "<a href=\"focusUser.jsp?uID=" + article.writerid + "\" class=\"btn btn-danger btn-sm\">关注</a>";
        }


        // 获取文章内容
        // todo:
        String articleText = operation.getBlogString(article);

        String info = "<div class=\"row bbs-article-section\">\n" +
                "                <div class=\"col-2 bbs-article-section-left\">\n" +
                "                    <div class=\"card\" style=\"width: 100%\">\n" +
                "                        <img class=\"card-img-top\" src=\"" + authorImgPath +
                "\"\n" +
                "                            alt=\"Card image\" style=\"width:100%\">\n" +
                "                        <div class=\"card-body text-center\">\n" +
                "                            <h6 class=\"card-title text-center\">" + authorName +
                "</h6>\n" +
                "                            <a href=\"" + "user_info.jsp?readInfo=other&readInfoID=" + Integer.toString(author.id) +
                "\" class=\"btn btn-light btn-sm\">More</a>\n" +
                focusUSer +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-8 bbs-article-section-center\">\n" +
                "                    <div id=\"article-info\">\n" +
                "                        <table class=\"table table-striped\">\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th>" + "发布时间: " + article.public_time + " | " + "标题 : " + article.title +
                "</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div id=\"article-text\">" + articleText +
                "</div>\n" +
                "                </div>\n" +
                "                <div class=\"col-2 bbs-article-section-right\">\n" +
                "                    <div class=\"list-group\">\n" +
                s +
                "                        <input type=\"button\" class=\"list-group-item list-group-item-primary text-center btn-sm\" " +
                "value=\"评论\" onclick=\"window.scrollTo(0, document.body.scrollHeight);\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>";

        System.out.println(operation.blogQueryById(1).msg);
        return info;
    }
    public  static String buildCommentSection(int logID, int commentAuthorID, String commentText, String commentTime) {
        userInfo user = operation.userQueryById(commentAuthorID);
        String uesrName = user.nickname;
        String userImagePath = user.head_img;

        String focusUSer = "";
        boolean isF = false;
        if (operation.isFriend(logID, commentAuthorID)) {
            isF = true;
            focusUSer = "<a href=\"#\" class=\"btn btn-danger btn-sm\">已关注</a>" ;
        } else {
            isF = false;
            focusUSer = "<a href=\"focusUser.jsp?uID=" + commentAuthorID + "\" class=\"btn btn-danger btn-sm\">关注</a>";
        }


        String info = "<div class=\"row bbs-comment-section\">\n" +
                "                <div class=\"col-2 bbs-comment-section-left\">\n" +
                "                    <div class=\"card\" style=\"width: 100%\">\n" +
                "                        <img class=\"card-img-top\" src=\"" + userImagePath +
                "\"\n" +
                "                            alt=\"Card image\" style=\"width:100%\">\n" +
                "                        <div class=\"card-body text-center\">\n" +
                "                            <h6 class=\"card-title text-center\">" + uesrName +
                "</h6>\n" +
                "                            <a href=\"" + "user_info.jsp?readInfo=other&readInfoID=" + Integer.toString(commentAuthorID) +
                "\" class=\"btn btn-light btn-sm\">More</a>\n" +
                focusUSer +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-10 bbs-comment-section-right\">\n" +
                "                    <div id=\"comment-info\">\n" +
                "                        <table class=\"table table-striped\">\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th>" + "回复时间 : " + commentTime +
                "</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div id=\"comment-text\">" + commentText +
                "</div>\n" +
                "                </div>\n" +
                "            </div>";

        return info;
    }

    public static String buildAllComment(int logID, int articleID) {
        commentInfoList commentList = operation.commentQueryByBlogId(articleID);
        int count = commentList.info.size();
        System.out.println(count);
        if (count == 0) {
            return "";
        }
        String info = "";
        for (int i = 0; i < count; i++) {
            // 获取评论内容 需要改
            // 改成获取路径
            // 读取路径文件
            // 成String类型
            // todo:
            String tempText = operation.getCommentString(commentList.info.get(i));
            String tempTime = commentList.info.get(i).publishtime;
            int commentAuthorID = commentList.info.get(i).commenterid;

            info += buildCommentSection(logID, commentAuthorID, tempText, tempTime);

        }
        System.out.println(commentList.info.get(0).publishtime);
        return info;
    }

}
