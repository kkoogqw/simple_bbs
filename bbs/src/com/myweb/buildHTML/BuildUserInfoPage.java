package com.myweb.buildHTML;

import com.myweb.sqlop.operation.*;
import com.myweb.sqlop.*;

public class BuildUserInfoPage {

    // ���Լ���ݷ���

    public static String buildSelfInfoPage(int userID) {
        userInfo user = operation.userQueryById(userID);
        String name = "value=\"" + user.nickname + "\"";
        String imagePath = user.head_img;
        String sex = user.sex;
        System.out.println(sex);
        if (sex==null || sex.equals("")) {
            sex = "x";
        }
        String email = "value=\"" + user.email_add + "\"";
        String tel = "value=\"" + user.phone_num + "\"";
        String assignment = user.sign;
        // todo:
        String birthday = "value=\"" + "" + "\"";
        String realName = "value=\"" + "" + "\"";

        String sexPart = "";
        if (sex.equals("m")) {
            sexPart= "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\" checked>��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\">Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\">����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        }
        else if (sex.equals("w")) {
            sexPart = "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\">��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\" checked>Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\">����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        } else {
            sexPart = "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\">��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\">Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\" checked>����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        }

        String info = "<div id=\"user-info-table\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-12\">\n" +
                "                    <!-- �����û�ͷ�� -->\n" +
                "                    <form name=\"img-update\" action=\"" + "headimgupload.jsp" +
                "\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
                "                        <input type=\"hidden\">\n" +
                "                        <div class=\"card bbs-user-img-card\">\n" +
                "                            <img class=\"card-img-top\" src=\"" + imagePath +
                "\"\n" +
                "                                alt=\"Card image\" style=\"width:100%\">\n" +
                "                            <div class=\"card-body\">\n" +
                "                                <div class=\"custom-file mb-3\" style=\"height: 20px\">\n" +
                "                                    <input type=\"file\" class=\"custom-file-input\" id=\"customFile\" name=\"filename\">\n" +
                "                                    <label class=\"custom-file-label\" for=\"customFile\">ѡ���ļ�</label>\n" +
                "                                </div>\n" +
                "                                <br>\n" +
                "                                <button class=\"btn btn-primary btn-block btn-sm\" type=\"submit\" name=\"update-img\"\n" +
                "                                    value=\"ok\">�ϴ�ͷ��</button>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </form>\n" +
                "\n" +
                "                    <div id=\"user-info-list\">\n" +
                "                        <form action=\"userUpdate.jsp?readInfo=self&readInfoID="+ userID +"\" method=\"POST\">\n" +
                "                            <h2>������Ϣ</h2>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"uname\">�û���</label>\n" +
                "                                <input type=\"text\" class=\"form-control\" id=\"uname\" name=\"username\" placeholder=\"�����û���\" " + name +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"email\">Email</label>\n" +
                "                                <input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\"\n" +
                "                                    placeholder=\"Enter email\"" + email +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"tel\">��ϵ�绰</label>\n" +
                "                                <input type=\"tel\" class=\"form-control\" id=\"tel\" name=\"tel\"\n" +
                "                                    placeholder=\"Enter your phone number\"" + tel + ">\n" +
                "                            </div>" +
                sexPart + "<div class=\"form-group\">\n" +
                "                                <label for=\"birth\">����</label>\n" +
                "                                <input type=\"date\" class=\"form-control\" id=\"birth\" name=\"birth\"" + birthday +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"real-name\">��ʵ����</label>\n" +
                "                                <input type=\"text\" class=\"form-control\" id=\"real-name\" name=\"real-name\"\n" +
                "                                    placeholder=\"Enter your Real Name!\"" + realName +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"introduce\">���˼��</label>\n" +
                "                                <textarea class=\"form-control\" rows=\"5\" id=\"introduce\" name=\"introduce\"\n" +
                "                                    placeholder=\"����һ�����Լ���!\">" + assignment +
                "</textarea>\n" +
                "                            </div>\n" +
                "                            <br>";
        info += "<h2>�����޸�</h2>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"org-pwd\">ԭ����</label>\n" +
                "                                <input type=\"password\" class=\"form-control\" id=\"org-pwd\" name=\"org-pwd\"\n" +
                "                                    placeholder=\"Enter password\">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"new-pwd\">������</label>\n" +
                "                                <input type=\"password\" class=\"form-control\" id=\"new-pwd\" placeholder=\"Enter password\">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"re-new-pwd\">���ظ�����������</label>\n" +
                "                                <input type=\"password\" class=\"form-control\" id=\"re-new-pwd\"\n" +
                "                                    placeholder=\"Enter password\">\n" +
                "                            </div>\n" +
                "                            <br>\n" +
                "                            <button type=\"submit\" class=\"btn btn-primary\">�ύ����</button>\n" +
                "                        </form>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>";

        return info;
    }

    // �������û�����

    public static String buildVisitorInfo(int visitorID) {
        userInfo visitor = operation.userQueryById(visitorID);
        String name = "value=\"" + visitor.nickname + "\"";
        String imagePath = visitor.head_img;
        String sex = visitor.sex;
        String email = "value=\"" + visitor.email_add + "\"";
        String tel = "value=\"" + visitor.phone_num + "\"";
        String assignment = visitor.sign;
        // todo:
        String birthday = "value=\"" + "" + "\"";
        String realName = "value=\"" + "" + "\"";

        String sexPart = "";
        if (sex == "male") {
            sexPart= "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\" checked>��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\">Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\">����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        }
        else if (sex == "female") {
            sexPart = "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\">��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\" checked>Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\">����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        } else {
            sexPart = "<div class=\"form-group\">\n" +
                    "                                <label for=\"sex\">�Ա�</label>\n" +
                    "                                <div class=\"radio\">\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"male\">��</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"female\">Ů</label>\n" +
                    "                                    <label><input type=\"radio\" name=\"sex\" id=\"sex\" value=\"none\" checked>����ʾ</label>\n" +
                    "                                </div>\n" +
                    "                            </div>";
        }

        String info = "<div id=\"user-info-table\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-12\">\n" +
                "                    <!-- �����û�ͷ�� -->\n" +
                "                    <form name=\"img-update\" action=\"" + "headimgupload.jsp" +
                "\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
                "                        <input type=\"hidden\">\n" +
                "                        <div class=\"card bbs-user-img-card\">\n" +
                "                            <img class=\"card-img-top\" src=\"" + imagePath +
                "\"\n" +
                "                                alt=\"Card image\" style=\"width:100%\">\n" +
                "                        </div>\n" +
                "                    </form>\n" +
                "\n" +
                "                    <div id=\"user-info-list\">\n" +
                "                        <form action=\"\" method=\"POST\">\n" +
                "                            <h2>������Ϣ</h2>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"uname\">�û���</label>\n" +
                "                                <input type=\"text\" class=\"form-control\" id=\"uname\" name=\"username\" placeholder=\"�����û���\" " + name +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"email\">Email</label>\n" +
                "                                <input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\"\n" +
                "                                    placeholder=\"Enter email\"" + email +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"tel\">��ϵ�绰</label>\n" +
                "                                <input type=\"tel\" class=\"form-control\" id=\"tel\" name=\"tel\"\n" +
                "                                    placeholder=\"Enter your phone number\"" + tel + ">\n" +
                "                            </div>" +
                sexPart + "<div class=\"form-group\">\n" +
                "                                <label for=\"birth\">����</label>\n" +
                "                                <input type=\"date\" class=\"form-control\" id=\"birth\" name=\"birth\"" + birthday +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"real-name\">��ʵ����</label>\n" +
                "                                <input type=\"text\" class=\"form-control\" id=\"real-name\" name=\"real-name\"\n" +
                "                                    placeholder=\"Enter your Real Name!\"" + realName +
                ">\n" +
                "                            </div>\n" +
                "                            <div class=\"form-group\">\n" +
                "                                <label for=\"introduce\">���˼��</label>\n" +
                "                                <textarea class=\"form-control\" rows=\"5\" id=\"introduce\" name=\"introduce\"\n" +
                "                                    placeholder=\"û�ж���......\">" + assignment +
                "</textarea>\n" +
                "                            </div>\n" +
                "                            <br>";
        info += "</form>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>";

        return info;
    }
}
