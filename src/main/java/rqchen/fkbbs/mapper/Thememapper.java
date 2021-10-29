package rqchen.fkbbs.mapper;

import org.apache.ibatis.annotations.*;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.Theme;

import java.util.List;

@Mapper
public interface Thememapper {
    @Select("select * from THEME order by LAST_REPLY desc")
    public List<Theme> getAllTheme();

    @Select("SELECT *\n" +
            "FROM THEME\n" +
            "WHERE USER_ID IN\n" +
            "    (SELECT USER_ID\n" +
            "     FROM USER\n" +
            "     WHERE USER_NAME LIKE  '%${user_name}%');")
    public List<Theme> searchThemebyUsername(String user_name);

    @Select("select * from THEME where THEME_TITLE like '%${search_title}%' order by LAST_REPLY desc")
    public List<Theme> searchThemebyTitle(String search_title);

    @Select("select * from THEME where THEME_Content like '%${search_content}%' order by LAST_REPLY desc")
    public List<Theme> searchThemebyContent(String search_content);

    @Select("select * from THEME where THEME_TITLE like '%${search_title}%' OR THEME_CONTENT like '%${search_content}%' order by LAST_REPLY desc")
    public List<Theme> searchThemebyAll(String search_title,String search_content);

    @Select("select * from THEME where THEME_ID=#{id}")
    public Theme getThemeById(Integer id);

    @Select("select * from THEME where USER_ID=#{id}")
    public List<Theme> getThemeByUserId(Integer id);

    @Delete("delete from THEME where THEME_ID=#{id}")
    public int delThemeById(Integer id);

    @Update("update THEME set THEME_TITLE=#{Theme_title},THEME_CONTENT=#{Theme_content} where THEME_ID=#{Theme_id}")
    public int UpdateTheme(Theme theme);

    @Insert("insert into THEME(THEME_ID,USER_ID,THEME_TITLE,THEME_CONTENT,THEME_TIME,LAST_REPLY) values (NULL,#{User_id},#{Theme_title},#{Theme_content},#{Theme_time},#{Theme_time})")
    public int InsertTheme(Theme theme);

    @Select("select count(THEME_ID) from THEME")
    public int getThemeCount();

    @Select("select THEME_TITLE from THEME where THEME_ID=#{id}")
    public String getThemeTitle(Integer id);

    @Update("update THEME set LAST_REPLY=#{Reply_time} where THEME_ID=#{Theme_id}")
    public int UpdateLastReply(Reply reply);

    @Select("select USER_ID from THEME where THEME_ID=#{id}")
    public String getUIDbytheme(Integer id);
}
