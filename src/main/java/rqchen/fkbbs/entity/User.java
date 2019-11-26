package rqchen.fkbbs.entity;

public class User {
    String User_id;
    String User_mail;
    String User_name;
    String User_sex;
    String User_birth;
    String Role;
    String User_password;

    public User(String email_reg, String nickname, String sex, String birth, String i, String password) {
        User_mail=email_reg;
        User_name=nickname;
        User_sex=sex;
        User_birth=birth;
        User_sex=i;
        User_password=password;
        Role="0";
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_mail() {
        return User_mail;
    }

    public void setUser_mail(String user_mail) {
        User_mail = user_mail;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_sex() {
        return User_sex;
    }

    public void setUser_sex(String user_sex) {
        User_sex = user_sex;
    }

    public String getUser_birth() {
        return User_birth;
    }

    public void setUser_birth(String user_birth) {
        User_birth = user_birth;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUser_password() {
        return User_password;
    }

    public void setUser_password(String user_password) {
        User_password = user_password;
    }

    @Override
    public String toString() {
        return "User{" +
                "User_id='" + User_id + '\'' +
                ", User_mail='" + User_mail + '\'' +
                ", User_name='" + User_name + '\'' +
                ", User_sex='" + User_sex + '\'' +
                ", User_birth='" + User_birth + '\'' +
                ", Role='" + Role + '\'' +
                ", User_password='" + User_password + '\'' +
                '}';
    }
}
