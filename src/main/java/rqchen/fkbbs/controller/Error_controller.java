package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ReplyService;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Error_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;
    @RequestMapping("/error_404")
    public String error404(HttpServletRequest request, Model model){
        List<Reply> replyList5=replyService.getNew5();
        for(Reply reply:replyList5){
            int user_id=Integer.parseInt(reply.getUser_id());
            String date=reply.getReply_time().substring(5,10);
            reply.setUser_name(userService.getUserName(user_id));
            reply.setReply_time(date);
            reply.setUser_img(userService.getById(user_id).getImg_url());
        }
        model.addAttribute("new5",replyList5);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "404";
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
        return "404";
    }
}
