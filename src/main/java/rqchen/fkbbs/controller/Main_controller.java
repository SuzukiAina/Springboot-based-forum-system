package rqchen.fkbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ReplyService;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Main_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;

    @RequestMapping("/hello")
    public String hello(){
        Date date=new Date();     //获取一个Date对象
        DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
        String Time = simpleDateFormat.format(date);   //格式化后的时间
        System.out.println(Time);
        return "hello";
    }
    
    @RequestMapping(value = "/verifymail")
    @ResponseBody
    public String verifymail(String email_reg){
        boolean result=true;
        User user=null;
        user=userService.verifymail(email_reg);
        if(user!=null){
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

    @GetMapping(value = "/main")
    public String main_page(@RequestParam(value ="page") int page,HttpServletRequest request,Model model){
        //总主题帖数，分页器实现
        int count=themeService.getCount();
        int page_number=(count/20)+1;
        int min_page,max_page;
        if(page_number>4){
            min_page=page_number-4;
            max_page=page_number;
        }else {
            min_page=1;
            max_page=page_number;
        }
        model.addAttribute("page_now",page);
        model.addAttribute("page_number",page_number);
        model.addAttribute("min_page",min_page);
        model.addAttribute("max_page",max_page);

        List<Theme> themeList=themeService.themeList();
        int min_num=(page-1)*20;
        int max_num;
        if(count>(page*20)){
            max_num=page*20;
        }else {
            max_num=count;
        }
        themeList=themeList.subList(min_num,max_num);
        for (Theme theme : themeList) {
            Integer ID=Integer.parseInt(theme.getTheme_id());//主题帖id
            Integer id=Integer.parseInt(theme.getUser_id());//发帖人id
            String time=theme.getTheme_time();
            theme.setUser_name(userService.getUserName(id));//发帖人
            theme.setReply_number(String.valueOf(replyService.getCount(ID)));//回帖数量
            theme.setTheme_time(theme.getTheme_time().substring(5,10));//主题帖时间
            //最新回复
            Reply newReply=replyService.getNewReply(ID);
            if(newReply!=null){
                Integer newReply_user_id=Integer.parseInt(newReply.getUser_id());
                theme.setNewRply_name(userService.getUserName(newReply_user_id));
            }
        }
        model.addAttribute("themes",themeList);
        //最近5条评论
        List<Reply> replyList5=replyService.getNew5();
        for(Reply reply:replyList5){
            int user_id=Integer.parseInt(reply.getUser_id());
            String date=reply.getReply_time().substring(5,10);
            reply.setUser_name(userService.getUserName(user_id));
            reply.setReply_time(date);
        }
        model.addAttribute("new5",replyList5);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "main";
        }
        System.out.println(user.toString());
        String role=user.getRole();
        System.out.println(role);
        if(role.equals("1")){
            System.out.println("是个管理员");
            model.addAttribute("ROLE",1);
        }else {
            System.out.println("是个用户");
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("USER_NAME",user.getUser_name());
        model.addAttribute("img_url",user.getImg_url());
        return "main";
    }

    @GetMapping(value = "")
    public String main_page(){
        return "redirect:/main?page=1";
    }
}
