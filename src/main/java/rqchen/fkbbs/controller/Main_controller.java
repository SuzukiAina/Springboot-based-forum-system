package rqchen.fkbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Date date=new Date();     //获取一个Date对象
        DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
        String Time = simpleDateFormat.format(date);   //格式化后的时间
        System.out.println(Time);
        return "hello";
    }

    @GetMapping(value = "/main")
    public String main(HttpServletRequest request,Model model){
        model.addAttribute("themes",themeService.themeList());
        int count=themeService.getCount();
        int page_number=(count/20)+1;
        System.out.println(page_number);
        int min_page,max_page;
        if(page_number>4){
            min_page=page_number-4;
            max_page=page_number;
        }else {
            min_page=1;
            max_page=page_number;
        }
        model.addAttribute("min_page",min_page);
        model.addAttribute("max_page",max_page);

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

        return "main";
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
