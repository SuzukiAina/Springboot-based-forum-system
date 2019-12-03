package rqchen.fkbbs.mapper;

import org.apache.ibatis.annotations.*;
import rqchen.fkbbs.entity.User;

import java.util.List;

@Mapper
public interface Usermapper {
    @Select("select * from USER")
    public List<User> getAllUser();

    @Select("select * from USER where USER_ID=#{id}")
    public User getUserById(Integer id);

    @Delete("delete from USER where USER_ID=#{id}")
    public int delUserById(Integer id);

    @Update("update USER set USER_MAIL=#{User_mail},USER_NAME=#{User_name},USER_SEX=#{User_sex}" +
            ",USER_BIRTH=#{User_birth},USER_PASSWORD=#{User_password} where USER_ID=#{User_id}")
    public int UpdateUser(User user);

    @Insert("insert into USER(USER_ID,USER_MAIL,USER_NAME,USER_SEX,USER_BIRTH,ROLE,USER_PASSWORD) " +
            "values (NULL,#{User_mail},#{User_name},#{User_sex},#{User_birth},#{Role},#{User_password})")
    public int InsertUser(User user);

    @Insert("insert into USER(USER_ID,USER_MAIL,USER_NAME,USER_SEX,USER_BIRTH,ROLE,USER_PASSWORD,IMG_URL) " +
            "values (NULL,#{User_mail},#{User_name},#{User_sex},#{User_birth},#{Role},#{User_password},#{Img_url})")
    public int InsertUserImg(User user);

    @Select("select USER_PASSWORD from USER where USER_MAIL=#{mail}")
    public String getUserByInfo(String mail);

    @Select("select * from USER where USER_MAIL=#{mail}")
    public User getUser(String mail);

    @Select("select * from USER where USER_MAIL=#{mail}")
    public User getUserByMail(String mail);

    @Select("select USER_NAME from USER where USER_ID=#{id}")
    public String getUserNmaeById(Integer id);
}
