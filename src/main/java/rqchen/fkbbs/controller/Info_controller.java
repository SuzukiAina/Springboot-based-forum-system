package rqchen.fkbbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Info_controller {
    @RequestMapping("/info")
    public String info(HttpServletRequest request, Model model){
        model.addAttribute("sector_now","user_info");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:main";
        }
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("login_user",user);
        return "info";
    }
}
