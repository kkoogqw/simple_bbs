package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;

public class BuildEditor {

    // �������ı��༭������
    public static String buildArticleEditor() {
        String info = "<div class=\"row\" id=\"article-editor-block\">\n" +
                "<br>" +
                "                <form action=\"blogUpload.jsp\" method=\"post\">\n" +
                "<h4>���±༭</h4>" +
                "                    <div class=\"input-group mb-3\">\n" +
                "                        <div class=\"input-group-prepend\">\n" +
                "                            <span class=\"input-group-text\">����</span>\n" +
                "                        </div>\n" +
                "                        <input type=\"text\" class=\"form-control\" placeholder=\"Your article title: \" id=\"usr\" name=\"article-title-input\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group\">\n" +
                "                        <label for=\"sel1\">�������� :</label>\n" +
                "                        <select class=\"form-control\" id=\"sel1\" name=\"article-type-choose\">\n" +
                "                            <option>1</option>\n" +
                "                            <option>2</option>\n" +
                "                            <option>3</option>\n" +
                "                            <option>4</option>\n" +
                "                        </select>\n" +
                "                    </div>\n" +
                "                    <div id=\"article-editor\">\n" +
                "                        <textarea id=\"summernote-article\" name=\"editordata\"></textarea>\n" +
                "                    </div>\n" +
                "                    <br>\n" +
                "                    <input type=\"submit\" class=\"btn btn-info\" value=\"�ύ\">\n" +
                "                </form>\n" +
                "            </div>\n";
        info += "<script src=\"script/article_editor.js\"></script>\n";

        return info;

    }

    public static String buildCommentEditor(int articleID) {
        System.out.println("haha+" + articleID);
        blogInfo article = operation.blogQueryById(articleID);
        String articleTitle = article.title;
        String articleAuthor = article.writer;
        String info = "<div id=\"comment-editor-block\">\n" +
                "                <form action=\"commentUpload.jsp?articleID=" + articleID + "\" method=\"post\">\n" +
                "                    <h4>��ӻظ� :" + articleTitle +
                " by: " + articleAuthor +
                "</h4>\n" +
                "                    <div id=\"comment-editor\">\n" +
                "                        <textarea id=\"summernote-comment\" name=\"editordata\"></textarea>\n" +
                "                    </div>\n" +
                "                    <br>\n" +
                "                    <input type=\"submit\" class=\"btn btn-info\" value=\"�ύ�ظ�\">\n" +
                "                </form>\n" +
                "            </div>\n"
                ;

        info += "<script src=\"script/comment_editor.js\"></script>\n";

        return info;
    }
}
