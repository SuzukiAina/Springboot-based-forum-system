package rqchen.fkbbs.entity;

import java.util.List;

public class Theme {
    String Theme_id;
    String Theme_title;
    String Theme_content;
    String Theme_time;
    String User_id;
    String User_name;
    String Reply_number;
    String NewRply_name;
    List<Reply> replyList;
    Reply NewReply;

    public String getNewRply_name() {
        return NewRply_name;
    }

    public void setNewRply_name(String newRply_name) {
        NewRply_name = newRply_name;
    }

    public Reply getNewReply() {
        return NewReply;
    }

    public void setNewReply(Reply newReply) {
        NewReply = newReply;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getReply_number() {
        return Reply_number;
    }

    public void setReply_number(String reply_number) {
        Reply_number = reply_number;
    }

    public String getTheme_id() {
        return Theme_id;
    }

    public void setTheme_id(String theme_id) {
        Theme_id = theme_id;
    }

    public String getTheme_title() {
        return Theme_title;
    }

    public void setTheme_title(String theme_title) {
        Theme_title = theme_title;
    }

    public String getTheme_content() {
        return Theme_content;
    }

    public void setTheme_content(String theme_content) {
        Theme_content = theme_content;
    }

    public String getTheme_time() {
        return Theme_time;
    }

    public void setTheme_time(String theme_time) {
        Theme_time = theme_time;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public Theme(String theme_title, String theme_content, String theme_time, String user_id) {
        Theme_title = theme_title;
        Theme_content = theme_content;
        Theme_time = theme_time;
        User_id = user_id;
    }
}
