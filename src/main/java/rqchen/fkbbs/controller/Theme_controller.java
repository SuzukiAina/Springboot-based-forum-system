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
        int ID=Integer.parseInt(id);
        Theme theme=themeService.getById(ID);
        Integer user_id=Integer.parseInt(theme.getUser_id());
        theme.setUser_name(userService.getUserName(user_id));
        theme.setReply_number(String.valueOf(replyService.getCount(ID)));
        model.addAttribute("theme",theme);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "theme_content";
        }
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
        return "theme_content";
    }
}
