package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Reg_controller {
    @Autowired
    UserService userService;
    @RequestMapping("/reg")
    public String reg(ModelMap map){
        return "reg";
    }

    @RequestMapping("/regsave")
    public String save(@RequestParam("email_reg")String email_reg, @RequestParam("nickname")String nickname, @RequestParam("password")String password, @RequestParam("date")String birth, @RequestParam("radio")String sex, HttpServletRequest request){
        User user=new User(email_reg,nickname,sex,birth,sex,password);
        user.toString();
        userService.insert(user);
        request.getSession().setAttribute("user",user);
        return "redirect:/main?page=1";
    }
}
