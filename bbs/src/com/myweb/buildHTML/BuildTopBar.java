package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;



public class BuildTopBar {

    // 构建顶栏
    public static String buildTopBar(String siteTitle, String linkName, String linkAddress, String key) {
        String info = "<nav class=\"navbar navbar-expand-sm bg-dark navbar-dark fixed-top\" id=\"top-bar\">\n" +
                "    <ul class=\"navbar-nav\">\n" +
                "        <li class=\"nav-item active\">\n" +
                "            <a class=\"nav-link\" href=\"home.jsp\">"+ siteTitle + "</a>\n" +
                "        </li>\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a class=\"nav-link\" href=\"";
        info += linkAddress;
        info += "\">" + linkName + "</a>";

        // todo : 搜索设置
        info += "</li>\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a class=\"nav-link\" href=\"help.jsp\">帮助中心</a>\n" +
                "        </li>\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a class=\"nav-link\" href=\"about.jsp\">关于</a>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "<a href=\"write_article.jsp\" class=\"btn btn-info btn-sm bbs-post-article\" role=\"button\">我要写东西!</a>" +
                "<form action=\"show_list.jsp?getList=article&getMethod=search&getKey=" + key + "\" method=\"post\" class=\"input-group mb-3 input-group-sm\" id=\"search-box\">\n" +
                "        <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"search-input\">\n" +
                "        <div class=\"input-group-append\">\n" +
                "            <button class=\"btn btn-success\" type=\"submit\">搜 索</button>\n" +
                "        </div>\n" +
                "    </form>\n" +
                "</nav>";


        return info;
    }

}
