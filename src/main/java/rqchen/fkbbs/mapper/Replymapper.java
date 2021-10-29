package rqchen.fkbbs.mapper;

import org.apache.ibatis.annotations.*;
import rqchen.fkbbs.entity.Reply;

import java.util.List;

@Mapper
public interface Replymapper {
    @Select("select * from REPLY")
    public List<Reply> getAllReply();

    @Select("select * from REPLY where REPLY_ID=#{id}")
    public Reply getReplyById(Integer id);

    @Select("select * from REPLY where REPLY_CONTENT like '%${search_content}%'")
    public List<Reply> searchReplybyContent(String search_content);

    @Select("SELECT *\n" +
            "FROM REPLY\n" +
            "WHERE USER_ID IN\n" +
            "    (SELECT USER_ID\n" +
            "     FROM USER\n" +
            "     WHERE USER_NAME LIKE  '%${user_name}%');")
    public List<Reply> searchReplybyUsername(String user_name);

    @Delete("delete from REPLY where REPLY_ID=#{id}")
    public int delReplyById(Integer id);

    @Update("update REPLY set REPLY_CONTENT=#{Reply_content} where REPLY_ID=#{Reply_id}")
    public int UpdateReply(Reply reply);

    @Insert("insert into REPLY(REPLY_ID,USER_ID,THEME_ID,REPLY_CONTENT,REPLY_TIME) values (NULL,#{User_id},#{Theme_id},#{Reply_content},#{Reply_time})")
    public int InsertReply(Reply reply);

    @Select("select count(REPLY_ID) from REPLY where THEME_ID=#{theme_id}")
    public int getReplyCount(Integer theme_id);

    @Select("select * from REPLY where THEME_ID=#{Theme_id}")
    public List<Reply> getReplybyTheme(int Theme_id);

    @Select("select * from REPLY where USER_ID=#{User_id}")
    public List<Reply> getReplybyUser(int User_id);

    @Select("select * from REPLY where THEME_ID=#{Theme_id} order by REPLY_TIME desc limit 1")
    public Reply getNewReply(int Theme_id);

    @Select("select * from REPLY where THEME_ID=#{Theme_id} order by REPLY_TIME asc")
    public List<Reply> getReplybyThemeasc(int Theme_id);

    @Select("select * from REPLY order by REPLY_TIME desc limit 5")
    public List<Reply> getNew5Reply();

    @Select("select USER_ID from REPLY where REPLY_ID=#{id}")
    public String getUIDbyReply(Integer id);
}
