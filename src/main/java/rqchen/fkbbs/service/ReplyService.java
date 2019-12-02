package rqchen.fkbbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.mapper.Replymapper;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    Replymapper replyMapper;

    public List<Reply> replyList(){
        return replyMapper.getAllReply();
    }

    public int insert(Reply reply){
        return replyMapper.InsertReply(reply);
    }

    public int delete(Integer id){
        return replyMapper.delReplyById(id);
    }

    public int update(Reply reply){
        return replyMapper.UpdateReply(reply);
    }

    public Reply getById(Integer id) {
        return replyMapper.getReplyById(id);
    }

    public int getCount(Integer theme_id){return replyMapper.getReplyCount(theme_id);}

    public List<Reply> getReply(Integer Theme_id){return replyMapper.getReplybyTheme(Theme_id);}

    public Reply getNewReply(Integer Theme_id){return replyMapper.getNewReply(Theme_id);}

    public List<Reply> getReplyasc(Integer Theme_id){return replyMapper.getReplybyThemeasc(Theme_id);}

    public List<Reply> getNew5(){return replyMapper.getNew5Reply();}
}
