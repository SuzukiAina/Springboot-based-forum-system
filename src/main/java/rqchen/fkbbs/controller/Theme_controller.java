package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Theme_controller {
    @Autowired
    ThemeService themeService;
    @RequestMapping("/{id}")
    public String theme_content(@PathVariable("id") String id, Model model, HttpServletRequest request){
        int ID=Integer.parseInt(id);
        Theme theme=themeService.getById(ID);
        model.addAttribute("themes",theme);
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null){
            model.addAttribute("USER_NAME",user.getUser_name());
        }
        return "theme_content";
    }
}
