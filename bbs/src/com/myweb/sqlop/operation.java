package com.myweb.sqlop;

import java.util.*;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.io.*;

public class operation {
    private static String connect_string="jdbc:mysql://localhost:3306/blog16338008"
            + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static String user="user";
    private static String pwd="123";

    public static void set_connect(String addr) {
        connect_string = "jdbc:mysql://" + addr
                + "/blog16338008?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        return;
    }

    public static void set_user(String _user, String _pwd){
        user = _user;
        pwd = _pwd;
    }

    //����û�
    public static int insertUser(String name, String passwd, int state){//state 0Ϊ�û� 1Ϊ����Ա �Լ�����״̬
        int msg = 2;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "insert into users(nickname, pwd, state) values('%s', '%s', '%d')";
            String sql = String.format(fmt, name, passwd, state);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;//����
            stmt.close();
            con.close();
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")) msg = 1;//�ظ��û����쳣
            else msg = 2;//�����쳣
        }
        return msg;
    }

    //����Ƿ�����
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //��֤�û���¼��Ϣ ����ͨ��id ��nickname���ַ�ʽ��¼ ����Ҫ��֤�Ƿ��ǹ���Ա
    public static int verifyLogIn(String name, String passwd, boolean admin) {
        userInfo us;
        int _state = admin? 0:1;//0�������Ա 1�Լ��������������ͨ�û���״̬
        if (isInteger(name)) {
            //ȫΪ���֣�תint���ͣ�ƥ��id
            int _id = Integer.parseInt(name);
            us = userQueryById(_id);
            if(us.msg == 0 && passwd.equals(us.pwd) && us.state<=_state) return 0;//�ɹ�
        }

        //��idƥ��ʧ�ܣ��κ�ԭ��
        us = userQueryByName(name);
        if(us.msg > 0) return us.msg;//�׳��쳣
        else if(passwd.equals(us.pwd) && us.state<=_state)  return 0;
        else return 1;//���� 0�ɹ� 1û��ƥ����˺Ż���������ĵ�¼��ʽ 2�����쳣
    }



    //�û��޸�����
    public static int updatePwd(int id, String passwd){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "update users set pwd='%s' where id='%d'";
            String sql = String.format(fmt, passwd, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            //msg = e.getMessage();
            msg = 1;//�����쳣
        }
        return msg;
    }

    //�û����ơ��޸ĸ�����Ϣ nickname email_add phone_num sex head_img����ͷ�񱣴����һ����ַ
    public static int userUpdateInfo(int id, String nickname, String _pwd, String email_add, String phone_num, String sex,String birthday, String truename, String sign){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "update users set nickname='%s', pwd='%s', email_add='%s', phone_num='%s', sex='%s', sign='%s', birthday='%s', truename='%s' where id='%d'";
            String sql = String.format(fmt, nickname, _pwd, email_add, phone_num, sex, sign, birthday, truename, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            //msg = e.getMessage();
            msg=1;//�����쳣
            System.out.println(e.getMessage());
        }
        return msg;
    }

    //ɾ���û�
    public static int deletetUser(int id){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "delete from users where id='%d'";
            String sql = String.format(fmt, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;//�����쳣
        }
        return msg;
    }

    //ʹ���෵�ض��ֵ
    public static class userInfo{
        public int id;
        public String nickname;
        public String pwd;
        public String email_add;
        public String phone_num;
        public String sex;
        public String create_time;
        public String head_img;
        public int state;
        public String sign;
        public int msg;
        public String birthday;
        public String truename;

        public userInfo() {
            id = 0;
            nickname = "";
            pwd = "";
            email_add = "";
            phone_num = "";
            sex = "";
            create_time = "";
            head_img = "";
            state = 1;
            sign = "";
            msg = 0;
            birthday = "";
            truename = "";
        }

        public void getmsg(ResultSet rs) {
            try {
                id = rs.getInt("id");
                nickname = rs.getString("nickname");
                pwd = rs.getString("pwd");
                email_add = rs.getString("email_add");
                phone_num = rs.getString("phone_num");
                sex = rs.getString("sex");
                create_time = rs.getTimestamp("create_time").toString();
                head_img = rs.getString("head_img");
                sign = rs.getString("sign");
                state = rs.getInt("state");
                try {
                    birthday = rs.getDate("birthday").toString();
                }catch(Exception e) {
                    birthday = "";//�����ƥ�䣬���ÿյ�
                }
                truename = rs.getString("truename");
                msg = 0;
            }catch(Exception e) {
                msg = 2;
                System.out.println(e.getMessage());
            }
        }
    }


    //�����û�����ѯ Ψһ
    public static userInfo userQueryByName(String _name){
        userInfo in = new userInfo();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from users where nickname='%s'";
            String sql = String.format(fmt, _name);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(sql);

            if(rs.next()) {
                in.getmsg(rs);
            }
            else {
                in.msg=1;//û��ƥ��Ľ��
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            in.msg = 2;//�����쳣
            System.out.println(e.getMessage());
        }
        return in;//���ص����У�msg��0����������1�����쳣
    }

    //����id��ѯ�û� Ψһ
    public static userInfo userQueryById(int _id){
        userInfo in = new userInfo();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from users where id='%d'";
            String sql = String.format(fmt, _id);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                in.getmsg(rs);
            }
            else {
                in.msg = 1;//û�н��
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            in.msg = 2;//�����쳣
            System.out.print(e.getMessage());
        }
        return in;
    }

    //��Ӳ���
    public static int insertBlog(String title, String contents, int writerid, int type, String _path){//·�����ļ��еĵ�ַ������ȷ���ļ�
        int msg=1;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");


            System.out.println("���벩��");
            //��������
            String fmt = "insert into blog(title, writerid, type) values('%s', '%d', '%d')";
            String sql = String.format(fmt, title, writerid, type);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;


            System.out.println("��ѯid");
            //��ѯid
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");//��ѯid
            int save_id = -1;
            if(rs.next()) save_id = rs.getInt(1);//��ȡid
            System.out.println("id="+save_id);


            System.out.println("����·�������ݿ�");
            //����·�������ݿ�
            String save_path = _path + save_id + ".txt";
            String fmt2 = "update blog set contents='%s' where id='%d'";
            String sql2 = String.format(fmt2, save_path, save_id);
            int cnt2 = stmt.executeUpdate(sql2);
            if(cnt2>0) msg = save_id;

            stmt.close();
            con.close();

            System.out.println("�����ļ�");
            //����Ϊ�ļ�
            File _f = new File(save_path);
            if(!_f.exists()) _f.createNewFile();
            Path path = _f.toPath();
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            writer.write(contents);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            msg = -1;
            System.out.println(e.getMessage());
        }
        return msg;
    }

    //�޸Ĳ��� �������ߡ��������ڵȲ����޸�
    public static int blogUpdateInfo(int id, String title, String contents, int likes, int views, int type){
        int msg=2;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "update blog set title='%s', contents='%s', likes='%d', views='%d', type='%d' where id='%d'";
            String sql = String.format(fmt, title, contents, likes, views, type, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            //msg = e.getMessage();
            msg=1;//�����쳣
        }
        return msg;
    }

    //ɾ������
    public static int deleteBlog(int blogid){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "delete from blog where id='%d'";
            String sql = String.format(fmt, blogid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    //������Ϣ
    public static class blogInfo{
        public int id;
        public String title;
        public String public_time;
        public String contents;
        public String writer;
        public int likes;
        public int views;
        public int writerid;
        public int type;
        public int msg;

        public blogInfo() {
            id = 0;
            title = "";
            public_time = "";
            writer = "";
            contents = "";
            likes = 0;
            views = 0;
            writerid = 0;
            type = 0;
            msg = 0;
        }

        public void getmsg(ResultSet rs) {
            try {
                id = rs.getInt("id");
                title = rs.getString("title");
                public_time = rs.getTimestamp("publish_time").toString();
                contents = rs.getString("contents");
                likes = rs.getInt("likes");
                views = rs.getInt("views");
                writerid = rs.getInt("writerid");
                type = rs.getInt("type");

                //��ѯ����������ǳ�
                Connection con2 = DriverManager.getConnection(connect_string, user, pwd);
                Statement stmt2 = con2.createStatement();
                String fmt2 = "select * from users where id='%d'";
                String sql2 = String.format(fmt2, writerid);
                ResultSet rs2 = stmt2.executeQuery(sql2);
                msg = 0;
                if(rs2.next()) writer = rs2.getString("nickname");
                else {
                    msg = 1;
                    System.out.println("error");
                }
                stmt2.close();
                con2.close();
            }catch(Exception e){
                msg = 1;
                System.out.println(e.getMessage());
            }
        }
    }

    //��������
    public static class blogInfoList{
        public List<blogInfo> info;
        public int msg;
        public int length;
        public blogInfoList() {
            info = new ArrayList<blogInfo>();
            msg = 0;
            length = 0;
        }
        public void add(blogInfo ele) {
            info.add(ele);
            length+=1;
        }
    }

    //������id��ѯ���� �����ж��
    public static blogInfoList blogQueryByWriterId(int _id){
        blogInfoList res = new blogInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from blog where writerid='%d'";
            String sql = String.format(fmt, _id);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                blogInfo in = new blogInfo();
                in.getmsg(rs);//��ȡȫ����Ϣ
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
        }
        return res;
    }

    //��id��ѯ����
    public static blogInfo blogQueryById(int _id){
        blogInfo in = new blogInfo();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from blog16338008.blog where id='%d'";
            String sql = String.format(fmt, _id);
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) in.getmsg(rs);
            else in.msg = 1;
            stmt.close();
            con.close();
        } catch (Exception e) {
            in.msg = 2;
            System.out.print(e.getMessage());
        }
        System.out.println(in.msg);
        return in;
    }

    //��id��ѯ����
    public static commentInfo commentQueryById(int _id){
        commentInfo in = new commentInfo();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from comment where id='%d'";
            String sql = String.format(fmt, _id);
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();//ֻ��һ��
            in.getmsg(rs);

            stmt.close();
            con.close();
        } catch (Exception e) {
            in.msg = 1;
        }
        return in;
    }

    //ģ����ѯ���͵ı��⡢����
    public static blogInfoList blogQueryByWord(String _word){
        blogInfoList res = new blogInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");

            String fmt = "select * from blog where title like '%s'"
                    + "or id in (select id from users where nickname like '%s')";//���
            String sql = String.format(fmt, "%"+_word+"%", "%"+_word+"%");
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {//��ò���id�б�
                //int blogid = rs.getInt("blogid");//����id
                //��ѯ
                //String fmt2 = "select * from blog where id='%d'";
                //String sql2 = String.format(fmt2, blogid);
                //ResultSet rs2 = stmt.executeQuery(sql2);//Ψһ
                //���²���Ϊ��ȡ������Ϣ
                //rs2.next();
                blogInfo in = new blogInfo();
                in.getmsg(rs);
                //����Ϣ��������
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
            System.out.println(e.getMessage());
        }
        return res;
    }


    //�������
    public static int insertComment(int blogid, int commenterid, String contents, String _path){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "insert into comment(blogid, commenterid) values('%d', '%d')";//��һ��������path
            String sql = String.format(fmt, blogid, commenterid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;

            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            int save_id = -1;
            if(rs.next()) save_id = rs.getInt(1);//��ȡid

            //����·�������ݿ�
            String save_path = _path + save_id + ".txt";
            String fmt2 = "update comment set contents='%s' where id='%d'";
            String sql2 = String.format(fmt2, save_path, save_id);
            int cnt2 = stmt.executeUpdate(sql2);
            if(cnt2>0) msg = 0;

            stmt.close();
            con.close();

            //����Ϊ�ļ�
            File _f = new File(save_path);
            if(!_f.exists()) _f.createNewFile();
            Path path = _f.toPath();
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            writer.write(contents);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            msg = 1;
            System.out.println(e.getMessage());
        }
        return msg;
    }

    //ɾ������
    public static int deleteComment(int id){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "delete from comment where id='%d'";
            String sql = String.format(fmt, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    public static class commentInfo{
        public int id;
        public int blogid;
        public int commenterid;
        public String commenter;
        public String publishtime;
        public String contents;
        public int msg;

        public commentInfo() {
            id = 0;
            blogid = 0;
            commenterid = 0;
            publishtime = "";
            contents="";
            commenter = "";
            msg = 0;
        }

        public void getmsg(ResultSet rs) {
            try {
                id = rs.getInt("id");
                blogid = rs.getInt("blogid");
                commenterid = rs.getInt("commenterid");
                publishtime = rs.getTimestamp("publishtime").toString();
                contents = rs.getString("contents");
                //��ѯ����������ǳ�
                Connection con2 = DriverManager.getConnection(connect_string, user, pwd);
                Statement stmt2 = con2.createStatement();
                String fmt2 = "select * from users where id='%d'";
                String sql2 = String.format(fmt2, commenterid);
                ResultSet rs2 = stmt2.executeQuery(sql2);
                if(rs2.next()) commenter = rs2.getString("nickname");
                else {
                    msg = 1;
                    System.out.println("error");
                }
                stmt2.close();
                con2.close();

            }catch(Exception e) {
                msg = 1;
                System.out.println("commentinfo error "+e.getMessage());
            }
        }
    }
    public static class commentInfoList{
        public List<commentInfo> info;
        public int msg;
        public int length;//�ܹ�ȡ�õ���������

        public commentInfoList() {
            info = new ArrayList<commentInfo>();
            length = 0;

        }
        public void add(commentInfo in) {
            info.add(in);
            length+=1;
        }
    }

    //���ݲ���id��ȡ��Ӧ����
    public static commentInfoList commentQueryByBlogId(int _id){
        commentInfoList info = new commentInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from comment where blogid='%d'";
            String sql = String.format(fmt, _id);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {//���
                commentInfo in = new commentInfo();
                in.getmsg(rs);
                info.add(in);
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            info.msg = 1;
            System.out.println("commentinfolist error "+e.getMessage());
        }
        return info;
    }

    //����������id��ȡ��Ӧ����
    public static commentInfoList commentQueryByUserId(int _id){
        commentInfoList info = new commentInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from comment where commenterid='%d'";
            String sql = String.format(fmt, _id);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {//���
                commentInfo in = new commentInfo();
                in.getmsg(rs);
                info.add(in);
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            info.msg = 1;
            System.out.println("commentinfolist error "+e.getMessage());
        }
        return info;
    }


    //��ӹ�ע�����ѣ�������
    public static int insertFriend(int userid, int friendid){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "insert into friend(userid, friendid) values('%d', '%d')";
            String sql = String.format(fmt, userid, friendid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    //ɾ������
    public static int deleteFriend(int userid, int friendid){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "delete from friend where userid='%d' and friendid = '%d'";
            String sql = String.format(fmt, userid, friendid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    //����ղ�
    public static int insertollect(int userid, int blogid){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "insert into collect(userid, blogid) values('%d', '%d')";
            String sql = String.format(fmt, userid, blogid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    //ɾ���ղ�
    public static int deleteCollect(int userid, int blogid){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "delete from collect where userid='%d'and blogid='%d'";
            String sql = String.format(fmt, userid, blogid);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;
        }
        return msg;
    }

    //�û��б�
    public static class userInfoList{
        public List<userInfo> list;
        public int msg;
        public int length;
        public userInfoList() {
            list = new ArrayList<userInfo>();
            msg = 0;
        }
        public void add(userInfo in) {
            list.add(in);
            length += 1;
        }
    }

    //���û�id��ѯ�����б� �ȸ���id��ѯ����id �ٸ��ݺ���id�г�����
    public static userInfoList friendQuery(int _userid){
        userInfoList res = new userInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");

            String fmt = "select * from friend where userid='%d'";//���
            String sql = String.format(fmt, _userid);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {//��ú���id�б�
                int friendid = rs.getInt("friendid");//�û�id
                //��ѯ��Ӧ�û�
                userInfo in = userQueryById(friendid);
                //����Ϣ��������
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
        }
        return res;
    }

    //���û�id��ѯ�ղ��б� �ȸ���id��ѯ�ղ� �ٸ����ղ�id�г�����
    public static blogInfoList collectQuery(int _userid){
        blogInfoList res = new blogInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");

            String fmt = "select * from collect where userid='%d'";//���
            String sql = String.format(fmt, _userid);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {//��ú���id�б�
                int blogid = rs.getInt("blogid");//�û�id
                //��ѯ��Ӧ�û�
                blogInfo in = blogQueryById(blogid);
                //����Ϣ��������
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
        }
        return res;
    }

    //��ȡȫ���û�
    public static userInfoList findUser_all(){
        userInfoList info = new userInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from users";
            String sql = String.format(fmt);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                userInfo in = new userInfo();
                in.getmsg(rs);
                info.add(in);
            }
            if (info.length==0) info.msg=1;//û���û�
            stmt.close();
            con.close();
        } catch (Exception e) {
            info.msg = 2;//�����쳣
        }
        return info;
    }

    //���ݷ������ڻ�ȡĳһtype����nƪ����
    public static blogInfoList getLatestBlog(int type, int n) {
        blogInfoList res = new blogInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");

            String fmt = "select * from blog where type='%d' order by publish_time desc limit %d";//���
            String sql = String.format(fmt, type, n);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {//��ò���id�б�
                blogInfo in = new blogInfo();
                in.getmsg(rs);
                //����Ϣ��������
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
            System.out.println(e.getMessage());
        }
        return res;
    }

    //���ݷ������ڻ�ȡĳһtypeȫ��
    public static blogInfoList getLatestBlog_all(int type) {
        blogInfoList res = new blogInfoList();
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");

            String fmt = "select * from blog where type='%d' order by publish_time desc";//���
            String sql = String.format(fmt, type);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {//��ò���id�б�
                blogInfo in = new blogInfo();
                in.getmsg(rs);
                //����Ϣ��������
                res.add(in);
            }
            res.msg = 0;
            stmt.close();
            con.close();
        } catch (Exception e) {
            res.msg = 1;
            System.out.println(e.getMessage());
        }
        return res;
    }

    //����ͷ�����ݿ�
    public static int updateHead(int id, String path){
        int msg=0;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "update users set head_img='%s' where id='%d'";
            String sql = String.format(fmt, path, id);
            int cnt = stmt.executeUpdate(sql);
            if(cnt>0) msg=0;

            stmt.close();
            con.close();
        } catch (Exception e) {
            msg = 1;//�����쳣
            System.out.print(e.getMessage());
        }
        return msg;
    }


    //��ȡ���� �����ݿ�ȡ��·����Ȼ��ȡ�������ֽ��������ַ�����ʽ����
    public static String getCommentString(commentInfo info){
        String msg="";
        try {
            Path path = FileSystems.getDefault().getPath(info.contents);
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            String line;
            while ((line = reader.readLine()) != null) {
                // һ�ζ���һ������
                msg += line;

            }
        } catch (Exception e) {
            msg = "error";//�����쳣
            System.out.print(e.getMessage());
        }
        return msg;
    }


    //��ȡ����ժҪ һ��
    public static String getCommentStringSummary(commentInfo info){
        String msg="";
        try {
            Path path = FileSystems.getDefault().getPath(info.contents);
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            String line;
            if ((line = reader.readLine()) != null) {
                // ����һ������
                msg += line;
            }
        } catch (Exception e) {
            msg = "error";//�����쳣
            System.out.print(e.getMessage());
        }
        return msg;
    }


    //��ȡ���� �����ݿ�ȡ��·����Ȼ��ȡ�������ֽ��������ַ�����ʽ����
    public static String getBlogString(blogInfo info){
        String msg="";
        try {
            Path path = FileSystems.getDefault().getPath(info.contents);
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            String line;
            while ((line = reader.readLine()) != null) {
                // һ�ζ���һ������
                msg += line;
                System.out.println(line);
            }
        } catch (Exception e) {
            msg = "error";//�����쳣
            System.out.println("error");
            System.out.println(e.getMessage());
        }
        return msg;
    }

    //����ҳ�룬�Ӳ����б����г���ҳ
    public static blogInfoList getBlogByPage(blogInfoList info, int page, int n){//�б�ҳ�룬һҳ����
        blogInfoList in = new blogInfoList();
        for(int i = 0; i < n; ++i) {
            int loc = (page-1)*n+i;
            if(info.length>loc) {
                in.add(info.info.get(loc));
            }
        }
        return in;
    }

    public static boolean isFriend(int userid1, int userid2) {

        boolean res = false;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from friend where userid=%d and friendid=%d";
            String sql = String.format(fmt, userid1, userid2);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                res = true;
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return res;
    }

    public static boolean isCollect(int userid, int blogid) {
        boolean res=false;
        try {
            Connection con = DriverManager.getConnection(connect_string, user, pwd);
            Statement stmt = con.createStatement();
            Class.forName("com.mysql.jdbc.Driver");
            String fmt = "select * from collect where userid=%d and blogid=%d";
            String sql = String.format(fmt, userid, blogid);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                res = true;
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return res;
    }
}