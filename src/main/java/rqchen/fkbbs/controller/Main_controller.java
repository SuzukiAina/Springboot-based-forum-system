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
    public String main_page(@RequestParam(required = false,value ="page",defaultValue = "1") int page,
                            @RequestParam(required = false,value ="search_content") String search_content,
                            HttpServletRequest request,Model model){
        List<Theme> themeList=null;
        if(search_content!=null){
            themeList=themeService.searchbyAll(search_content,search_content);
        }else{
            themeList=themeService.themeList();
        }
        //总主题帖数，分页器实现
        int count=themeList.size();
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
            reply.setUser_name(userService.getById(user_id).getUser_name());
            reply.setReply_time(date);
            reply.setUser_img(userService.getById(user_id).getImg_url());
        }
        model.addAttribute("new5",replyList5);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "main";
        }
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("login_user",user);
        model.addAttribute("USER_NAME",user.getUser_name());
        model.addAttribute("img_url",user.getImg_url());
        return "main";
    }

    @RequestMapping("/main_search")
    public String main_search(@RequestParam("search_content")String search_content){
        return "redirect:/main?search_content="+search_content;
    }

    @GetMapping(value = "")
    public String main_page(){
        return "redirect:/main?page=1";
    }
}
