package rqchen.fkbbs.entity;

public class Theme {
    String Theme_id;
    String Theme_title;
    String Theme_content;
    String Theme_time;
    String User_id;

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
