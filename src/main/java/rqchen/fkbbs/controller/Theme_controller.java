package rqchen.fkbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            reply.setUser_img(userService.getById(user_id).getImg_url());
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
            User replyuser=userService.getById(Integer.parseInt(reply.getUser_id()));
            reply.setUser_name(name);
            String date=reply.getReply_time().substring(5,10);
            reply.setReply_time(date);
            reply.setFloor(floor);
            reply.setUser_img(replyuser.getImg_url());
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
        model.addAttribute("login_user",user);
        model.addAttribute("img_url",user.getImg_url());
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
        themeService.lastreply(reply);
        return "redirect:/theme?id="+theme_id;
    }

    @RequestMapping(value = "/verifylogin")
    @ResponseBody
    public String verifymail(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        boolean result=true;
        if(user==null){
            result=false;
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
