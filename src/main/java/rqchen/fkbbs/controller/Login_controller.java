package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class Login_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;
    @PostMapping(value = "/loginpost")
    public String loginPost(@RequestParam("email_login")String email_login, @RequestParam("password")String password, Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(email_login);
        System.out.println(password);
        String pwd=userService.login(email_login);
        System.out.println(pwd);
        if(pwd==null){
            map.put("msg", "用户名不存在");
            return "login";
        }
        if (!pwd.equals(password)) {
            map.put("msg", "密码错误");
            return "login";
        }else {
            User user=null;
            user=userService.getUser(email_login);
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
