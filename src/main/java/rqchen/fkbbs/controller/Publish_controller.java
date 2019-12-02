package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Publish_controller {
    @Autowired
    ThemeService themeService;
    @RequestMapping("/publish")
    public String pulish(HttpServletRequest request, Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/main?page=1";
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
        return "publish";
    }

    @RequestMapping("/publishinsert")
    public String publishinsert(@RequestParam("title")String title, @RequestParam("description")String description, HttpServletRequest request){
        Date date=new Date();     //获取一个Date对象
        DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
        String Time = simpleDateFormat.format(date);   //格式化后的时间
        User user = (User)request.getSession().getAttribute("user");
        Theme theme=new Theme(title,description,Time,user.getUser_id());
        themeService.insert(theme);
        return "redirect:/main?page=1";
    }
}
