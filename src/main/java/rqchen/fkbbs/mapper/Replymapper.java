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

    @Delete("delete from REPLY where REPLY_ID=#{id}")
    public int delReplyById(Integer id);

    @Update("update REPLY REPLY_CONTENT=#{Reply_content} where REPLY_ID=#{Reply_id}")
    public int UpdateReply(Reply reply);

    @Insert("insert into REPLY(REPLY_ID,USER_ID,THEME_ID,REPLY_CONTENT,REPLY_TIME) values (NULL,#{User_id},#{Theme_id},#{Reply_content},#{Reply_time})")
    public int InsertReply(Reply reply);
}
