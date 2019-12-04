package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
public class Reg_controller {
    @Autowired
    UserService userService;
    @RequestMapping("/reg")
    public String reg(ModelMap map){
        return "reg";
    }

    @RequestMapping("/regsave")
    public String save(@RequestParam("email_reg")String email_reg, @RequestParam("nickname")String nickname, @RequestParam("password")String password, @RequestParam("date")String birth, @RequestParam("radio")String sex, @RequestParam("img")MultipartFile file, HttpServletRequest request){
        if(!Objects.equals(file.getOriginalFilename(), "")){
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(suffixName);
            fileName = UUID.randomUUID()+suffixName;
            System.out.println(fileName);
            String path = request.getSession().getServletContext().getRealPath("") + "upload"+ File.separator+fileName;
            String pathfile=File.separator+"upload"+File.separator+fileName;
            File file1 = new File(path);
            if(!file1.getParentFile().exists()){
                file1.getParentFile().mkdir();
            }
            System.out.println(path);
            try {
                //将图片保存到static文件夹里
                file.transferTo(new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            User user=new User(email_reg,nickname,sex,birth,password,pathfile);
            userService.insertImg(user);
            user=userService.getUser(email_reg);
            request.getSession().setAttribute("user",user);
        }else {
            User user=new User(email_reg,nickname,sex,birth,sex,password);
            userService.insert(user);
            user=userService.getUser(email_reg);
            request.getSession().setAttribute("user",user);
        }
        return "redirect:/main?page=1";
    }
}
