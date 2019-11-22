package rqchen.fkbbs.entity;

public class Reply {
    String Reply_id;
    String Theme_id;
    String Reply_content;
    String Reply_time;
    String User_id;

    public String getReply_id() {
        return Reply_id;
    }

    public void setReply_id(String reply_id) {
        Reply_id = reply_id;
    }

    public String getTheme_id() {
        return Theme_id;
    }

    public void setTheme_id(String theme_id) {
        Theme_id = theme_id;
    }

    public String getReply_content() {
        return Reply_content;
    }

    public void setReply_content(String reply_content) {
        Reply_content = reply_content;
    }

    public String getReply_time() {
        return Reply_time;
    }

    public void setReply_time(String reply_time) {
        Reply_time = reply_time;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }
}
