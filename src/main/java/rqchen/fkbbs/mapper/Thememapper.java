package rqchen.fkbbs.mapper;

import org.apache.ibatis.annotations.*;
import rqchen.fkbbs.entity.Theme;

import java.util.List;

@Mapper
public interface Thememapper {
    @Select("select * from THEME")
    public List<Theme> getAllTheme();

    @Select("select * from THEME where THEME_ID=#{id}")
    public Theme getThemeById(Integer id);

    @Delete("delete from THEME where THEME_ID=#{id}")
    public int delThemeById(Integer id);

    @Update("update THEME set THEME_TITLE=#{Theme_title},THEME_CONTENT=#{Theme_content} where THEME_ID=#{Theme_id}")
    public int UpdateTheme(Theme theme);

    @Insert("insert into THEME(THEME_ID,USER_ID,THEME_TITLE,THEME_CONTENT,THEME_TIME) values (NULL,#{User_id},#{Theme_title},#{Theme_content},#{Theme_time})")
    public int InsertTheme(Theme theme);

    @Select("select count(THEME_ID) from THEME")
    public int getThemeCount();
}
