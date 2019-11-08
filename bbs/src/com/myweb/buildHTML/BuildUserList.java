package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;

public class BuildUserList {

    public static String buildUserLine(int id, String userName, String sex, String regTime, boolean isFocus) {
        String info = "<a href=\"user_info.jsp?readInfo=other&readInfoID=" + id +"\" class=\"list-group-item list-group-item-action\">\n" +
                "                        <div class=\"row\">\r\n";
        String namePart = "<div class=\"col-sm-3\">" + userName + "</div>\r\n";
        String sexPart = "<div class=\"col-sm-3\">" + sex + "</div>\r\n";
        String timePart  = "<div class=\"col-sm-3\">" + regTime + "</div>\r\n";
        String focusPart = "";
        if (isFocus) {
            focusPart = "<div class=\"col-sm-3\"><object><a href=\"unfocusUser.jsp?uID=" + id + "\" class=\"btn btn-info btn-sm\" role=\"button\">取关</a></object></div>\r\n";
        } else {
            focusPart = "<div class=\"col-sm-3\"><object><a href=\"focusUser.jsp?uID=" + id + "\" class=\"btn btn-info btn-sm\" role=\"button\">关注</a></object></div>\r\n";
        }
        //focusPart = "<div class=\"col-sm-3\"><object><a href=\"unfocusUser.jsp?uID=" + id + "\"  class=\"btn btn-info btn-sm\" role=\"button\">取关</a></object></div>\r\n";

        info += namePart + sexPart + timePart + focusPart;



        info += "</div>\n" +
                "                    </a>";

        return info;
    }

    public static String buildUserList(userInfoList userList, int logID) {
        int count = userList.list.size();
        String info = "<div class=\"list-group bbs-block-list\">\r\n";
        for (int i = 0; i < count; i++) {
            int id = userList.list.get(i).id;
            String userName = userList.list.get(i).nickname;
            String sex = userList.list.get(i).sex;
            String time = userList.list.get(i).create_time.substring(0, 10);
            //String time = (String)userList.list.get(i).create_time;
            // todo:
            boolean isFocus = false;
            isFocus = operation.isFriend(id, logID);

            info += buildUserLine(id, userName, sex, time, isFocus);
        }
        info += "</div>\n";

        return info;
    }
}
