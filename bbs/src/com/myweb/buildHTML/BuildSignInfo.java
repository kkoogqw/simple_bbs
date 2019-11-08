package com.myweb.buildHTML;

import java.io.*;
import java.util.*;

public class BuildSignInfo {

    public static String buildErrorInfo(boolean type, int serverCode) {
        // type : true -> 登录
        //      : false -> 注册
        String errInfo = "";
        if (type) {
            if (serverCode == 1) {
                errInfo = "请检查用户名、密码和登录方式!";
            } else {
                errInfo = "";
            }
        } else {
            if (serverCode == 1 || serverCode == 2) {
                errInfo = "用户名已存在!";
            } else if (serverCode == 3) {
                errInfo = "两次输入的密码不一致!";
            } else if (serverCode == 4) {
                errInfo = "输入不能为空!";
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
        // type : true -> 登录
        //      : false -> 注册
        if (type == true) {
            text = "<strong>成功! 正在跳转... ...</strong>\r\n";
        } else {
            text = "<strong>成功! 正在跳转... ...</strong>\r\n";
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
        // type : true -> 登录
        //      : false -> 注册
        if (type) {
            text = "<strong>失败! " + errorInfo +"</strong>\r\n";
        } else {
            text = "<strong>失败! " + errorInfo + "</strong>\r\n";
        }
        info += "<div class=\"alert alert-danger alert-dismissible fade show\">\r\n";
        info += "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\r\n";
        info += text;
        info += "</div>\r\n";

        return info;
    }

}
