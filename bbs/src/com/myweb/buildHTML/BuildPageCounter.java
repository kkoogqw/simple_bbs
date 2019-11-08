package com.myweb.buildHTML;

public class BuildPageCounter {
    public static String buildPageCounter(int nowPage, int totlePages) {
        String info = "<ul class=\"pagination bbs-page-choose\">\r\n";
        if (nowPage==1) {
            info += "<li class=\"page-item disabled\">\n" +
                    "                        <a class=\"page-link\" href=\"#\">前一页</a>\n" +
                    "                    </li>";
        } else {
            info += "<li class=\"page-item\">\n" +
                    "                        <a class=\"page-link\" href=\"show_list.jsp?getPage=" + (nowPage-1) + "\">前一页</a>\n" +
                    "                    </li>";
        }
        for (int i = 0; i < totlePages; i++) {
            if (i + 1 == nowPage) {
                info += "<li class=\"page-item active\">\n" +
                        "                        <a class=\"page-link\" href=\"#\">";
                info += Integer.toString(i+1);
                info += "</a>\n" +
                        "                    </li>";
            } else {
                info += "<li class=\"page-item\">\n" +
                        "                        <a class=\"page-link\" href=\"show_list.jsp?getPage=" + (i+1) +"\">";
                info += Integer.toString(i+1);
                info += "</a>\n" +
                        "                    </li>";
            }
        }
        if (nowPage == totlePages) {
            info += "<li class=\"page-item disabled\">\n" +
                    "                        <a class=\"page-link\" href=\"#\">后一页</a>\n" +
                    "                    </li>";
        } else {
            info += "<li class=\"page-item\">\n" +
                    "                        <a class=\"page-link\" href=\"show_list.jsp?getPage=" + (nowPage+1) + "\">后一页</a>\n" +
                    "                    </li>";
        }
        return info;
    }
}
