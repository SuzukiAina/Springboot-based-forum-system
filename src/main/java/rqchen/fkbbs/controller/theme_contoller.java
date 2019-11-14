package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.service.ThemeService;

@Controller
public class theme_contoller {
    @Autowired
    ThemeService themeService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/main")
    public String themeList(Model model){
        model.addAttribute("themes",themeService.themeList());
        return "main";
    }
}
