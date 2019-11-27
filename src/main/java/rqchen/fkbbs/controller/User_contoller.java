package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rqchen.fkbbs.service.UserService;

@Controller
public class User_contoller {
    @Autowired
    UserService userService;

    @RequestMapping("/admin_user")
    public String admin_user(){
        return "user_list";
    }
}
