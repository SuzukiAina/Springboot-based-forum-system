package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Theme_controller {
    @Autowired
    ThemeService themeService;
    @RequestMapping("/theme")
    public String theme_content(@RequestParam(value ="id") String id, Model model, HttpServletRequest request){
        int ID=Integer.parseInt(id);
        Theme theme=themeService.getById(ID);
        model.addAttribute("themes",theme);

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "main";
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
