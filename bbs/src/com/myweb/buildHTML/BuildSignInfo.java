package com.myweb.buildHTML;

import java.io.*;
import java.util.*;

public class BuildSignInfo {

    public static String buildErrorInfo(boolean type, int serverCode) {
        // type : true -> ��¼
        //      : false -> ע��
        String errInfo = "";
        if (type) {
            if (serverCode == 1) {
                errInfo = "�����û���������͵�¼��ʽ!";
            } else {
                errInfo = "";
            }
        } else {
            if (serverCode == 1 || serverCode == 2) {
                errInfo = "�û����Ѵ���!";
            } else if (serverCode == 3) {
                errInfo = "������������벻һ��!";
            } else if (serverCode == 4) {
                errInfo = "���벻��Ϊ��!";
            }
            else {
                errInfo = "";
            }
        }
        return errInfo;
    }

    public static String buildSuccessInfo(boolean type) {
        String info = "";
        String text = "";
        // type : true -> ��¼
        //      : false -> ע��
        if (type == true) {
            text = "<strong>�ɹ�! ������ת... ...</strong>\r\n";
        } else {
            text = "<strong>�ɹ�! ������ת... ...</strong>\r\n";
        }
        info += "<div class=\"alert alert-success alert-dismissible fade show\">\r\n";
        info += "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\r\n";
        info += text;
        info += "</div>\r\n";

        return info;
    }

    public static String buildFailInfo(boolean type, int serverCode) {
        String info = "";
        String text = "";
        String errorInfo = buildErrorInfo(type, serverCode);
        // type : true -> ��¼
        //      : false -> ע��
        if (type) {
            text = "<strong>ʧ��! " + errorInfo +"</strong>\r\n";
        } else {
            text = "<strong>ʧ��! " + errorInfo + "</strong>\r\n";
        }
        info += "<div class=\"alert alert-danger alert-dismissible fade show\">\r\n";
        info += "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\r\n";
        info += text;
        info += "</div>\r\n";

        return info;
    }

}
