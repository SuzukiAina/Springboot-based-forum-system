package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ReplyService;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class Theme_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;
    @RequestMapping("/theme")
    public String theme_content(@RequestParam(value ="id") String id, Model model, HttpServletRequest request){
        //最近5条评论
        List<Reply> replyList5=replyService.getNew5();
        for(Reply reply:replyList5){
            int user_id=Integer.parseInt(reply.getUser_id());
            String date=reply.getReply_time().substring(5,10);
            reply.setUser_name(userService.getUserName(user_id));
            reply.setReply_time(date);
        }
        model.addAttribute("new5",replyList5);
        //主题帖展示
        int ID=Integer.parseInt(id);
        Theme theme=themeService.getById(ID);
        Integer user_id=Integer.parseInt(theme.getUser_id());
        theme.setUser_name(userService.getUserName(user_id));
        theme.setReply_number(String.valueOf(replyService.getCount(ID)));
        model.addAttribute("theme",theme);

        //回复展示
        List<Reply> replyList=replyService.getReplyasc(ID);
        int floor=1;
        for(Reply reply:replyList){
            floor=floor+1;
            int user=Integer.parseInt(reply.getUser_id());
            String name=userService.getUserName(user);
            reply.setUser_name(name);
            String date=reply.getReply_time().substring(5,10);
            reply.setReply_time(date);
            reply.setFloor(floor);
        }
        model.addAttribute("replies",replyList);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "theme_content";
        }
        String role=user.getRole();
        System.out.println(role);
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("USER_NAME",user.getUser_name());
        return "theme_content";
    }

    @RequestMapping("/commentinsert")
    public String commentinsert(@RequestParam("theme_id")String theme_id,@RequestParam("comment")String comment,HttpServletRequest request){
        Date date=new Date();     //获取一个Date对象
        DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
        String Time = simpleDateFormat.format(date);   //格式化后的时间
        User user = (User)request.getSession().getAttribute("user");
        Reply reply=new Reply(theme_id,comment,Time,user.getUser_id());
        replyService.insert(reply);
        return "redirect:/main?page=1";
    }
}
