package rqchen.fkbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;


import java.util.HashMap;
import java.util.Map;

@Controller
public class Main_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/main")
    public String themeList(Model model){
        model.addAttribute("themes",themeService.themeList());
        model.addAttribute("USER_NAME",userService.getById(4).getUser_name());
        return "main";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "";
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
}
